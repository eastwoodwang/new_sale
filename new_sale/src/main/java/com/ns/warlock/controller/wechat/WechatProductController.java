package com.ns.warlock.controller.wechat;

import com.ns.warlock.common.Result;
import com.ns.warlock.dto.ProductDTO;
import com.ns.warlock.service.ProductImageService;
import com.ns.warlock.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/product")
@CrossOrigin
public class WechatProductController extends BaseController {

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Resource(name = "productImageServiceImpl")
    private ProductImageService productImageService;

    /**
     * 获取全部商品
     * @return
     */
    @PostMapping(value = "/extractProducts")
    @ApiOperation(value = "获取商品", notes = "获取上架所有商品")
    public @ResponseBody
    Result extractProducts () {
        return new Result("0", SUCCESS_MESSAGE, productService.findAll());
    }

    /**
     * 获取某一商品
     * @param productId
     * @return
     */
    @PostMapping(value = "/extractProduct")
    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    public @ResponseBody Result extractProduct (@ApiParam(required = true, name = "productId", value = "商品ID")@RequestParam Long productId) {
        //获取商品
        ProductDTO productDTO = productService.find(productId);
        if (productDTO != null) {
            //获取商品图片列表
            productDTO.setProductImages(productImageService.findByProductSn(productDTO.getSn()));
        }
        return new Result("0", SUCCESS_MESSAGE, productDTO);
    }


}
