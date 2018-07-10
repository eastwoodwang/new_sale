package com.ns.warlock.controller.admin;

import com.ns.warlock.FileInfo;
import com.ns.warlock.common.Message;
import com.ns.warlock.dto.ProductDTO;
import com.ns.warlock.dto.ProductImageDTO;
import com.ns.warlock.dto.SnDTO;
import com.ns.warlock.service.FileService;
import com.ns.warlock.service.ProductImageService;
import com.ns.warlock.service.ProductService;
import com.ns.warlock.service.SnService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller("productController")
@RequestMapping("/admin/product")
public class ProductController extends BaseController {

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "productImageServiceImpl")
    private ProductImageService productImageService;

    @Resource(name = "snServiceImpl")
    private SnService snService;

    @Resource(name = "fileServiceImpl")
    private FileService fileService;


    @GetMapping(value = "/list")
    public String list(ModelMap modelMap) {
        modelMap.addAttribute("list", productService.findAll());
        return "/admin/product/list";
    }


    @GetMapping(value = "/add")
    public String add() {
        return "/admin/product/add";
    }


    @PostMapping(value = "/save")
    public String save(ProductDTO productDTO) {

        //设置商品编号
        String productSn = snService.generate(SnDTO.Type.product);
        productDTO.setSn(productSn);

        //设置商品其他属性

        //会员价
        if (productDTO.getMemberPrice() == null) {
            productDTO.setMemberPrice(productDTO.getPrice());
        }

        //已分配库存
        productDTO.setAllocateStock(0);
        //默认上架
        if (StringUtils.isEmpty(productDTO.getMarketable())) {
            productDTO.setMarketable("1");
        }
        //创建时间
        productDTO.setCreateDate(new Date());


        //将图片上传
        if (productDTO.getProductImages() != null) {
            for (ProductImageDTO productImageDTO : productDTO.getProductImages()) {
                productImageService.build(productImageDTO);
                productImageDTO.setProductSn(productSn);

                //保存持久化
                productImageService.insert(productImageDTO);
            }
            //调整图片的顺序
            Collections.sort(productDTO.getProductImages());

            //并获取缩略图
            productDTO.setDisplayImage(productDTO.getProductImages().get(0).getThumbnail());
        }

        //最后存放商品
        productService.save(productDTO);

        return "redirect:list.jhtml";
    }

    /**
     * 商品编辑
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit (Long id, ModelMap modelMap) {
        ProductDTO productDTO = productService.find(id);
        productDTO.setProductImages(productImageService.findByProductSn(productDTO.getSn()));
        modelMap.addAttribute("productDTO", productDTO);
        return "/admin/product/edit";
    }

    /**
     * 商品修改
     * @param productDTO
     * @return
     */
    @PostMapping(value = "/update")
    public String update(ProductDTO productDTO) {
        //检查图片的内容是否正确
        for (Iterator<ProductImageDTO> iterator = productDTO.getProductImages().iterator(); iterator.hasNext();) {
            ProductImageDTO productImageDTO = iterator.next();
            if (productImageDTO == null || productImageDTO.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (productImageDTO.getFile() != null && !productImageDTO.getFile().isEmpty()) {
                if (!fileService.isValid(FileInfo.FileType.image, productImageDTO.getFile())) {
                    return "redirect:edit.jhtml?id=" + productDTO.getId();
                }
            }
        }

        //删除、修改和新增加
        //将图片上传
        if (productDTO.getProductImages() != null) {
            for (ProductImageDTO productImageDTO : productDTO.getProductImages()) {
                productImageService.build(productImageDTO);
                productImageDTO.setProductSn(productDTO.getSn());

                //保存持久化
                productImageService.insert(productImageDTO);
            }
            //调整图片的顺序
            Collections.sort(productDTO.getProductImages());

            //并获取缩略图
            productDTO.setDisplayImage(productDTO.getProductImages().get(0).getThumbnail());
        }


        //修改商品
        productService.update(productDTO);
        return "redirect:list.jhtml";
    }


    /**
     * 批量上架
     * @param ids
     * @return
     */
    @PostMapping(value = "/marketable")
    public @ResponseBody Message marketable (Long[] ids) {
        Message message = new Message(Message.Type.success, "所选数据已经操作成功");

        productService.batchToggleMarketable(ids, "1");

        return message;
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @PostMapping(value = "/inmarketable")
    public @ResponseBody Message inmarketable (Long[] ids) {
        Message message = new Message(Message.Type.success, "所选数据已经操作成功");

        productService.batchToggleMarketable(ids, "2");

        return message;
    }


    /**
     * 删除商品 最要为下架使用
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete")
    public @ResponseBody Message delete(Long[] ids) {
        Message message = null;
        boolean checkPass = true;

        if (checkPass) {
            productService.delete(ids);
            message = new Message(Message.Type.success, "该数据已经成功删除");
        }
        return message;
    }
}
