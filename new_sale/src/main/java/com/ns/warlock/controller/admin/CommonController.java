package com.ns.warlock.controller.admin;

import com.ns.warlock.dto.AdminDTO;
import com.ns.warlock.service.AdminService;
import com.ns.warlock.service.CaptchaService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Controller("adminCommonController")
@RequestMapping("/admin/common")
public class CommonController extends BaseController implements ServletContextAware {

    @Resource(name = "captchaServiceImpl")
    private CaptchaService captchaService;

    @Resource(name = "adminServiceImpl")
    private AdminService adminService;

    /** servletContext */
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 主页
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "/admin/common/main";
    }

    /**
     * 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "/admin/common/index";
    }


    /**
     * 用户profile
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile () {
        return "/admin/common/profile";
    }

    /**
     * 验证码
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void image(String captchaId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(captchaId)) {
            captchaId = request.getSession().getId();
        }
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            BufferedImage bufferedImage = captchaService.buildImage(captchaId);
            ImageIO.write(bufferedImage, "jpg", servletOutputStream);
            servletOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(servletOutputStream);
        }
    }


    /**
     * 验证当前密码
     */
    @RequestMapping(value = "/check_current_password", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkCurrentPassword(String currentPassword) {
        if (StringUtils.isEmpty(currentPassword)) {
            return false;
        }
        AdminDTO adminDTO = adminService.getCurrent();
        if (StringUtils.equals(DigestUtils.md5Hex(currentPassword), adminDTO.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save (String currentPassword, String password) {
        AdminDTO adminDTO  = adminService.getCurrent();
        if (StringUtils.isNotEmpty(currentPassword) && StringUtils.isNotEmpty(password)) {

            if (!StringUtils.equals(DigestUtils.md5Hex(currentPassword), adminDTO.getPassword())) {
                return ERROR_VIEW;
            }
            adminDTO.setPassword(DigestUtils.md5Hex(password));
        }

        adminService.updatePassword(adminDTO);
        return "redirect:main.jhtml";
    }

    /**
     * 错误提示
     */
    @RequestMapping(value = "/error")
    public String error() {
        return "/admin/common/error";
    }
}
