package com.ns.warlock.controller.admin;

import com.ns.warlock.FileInfo;
import com.ns.warlock.FileInfo.FileType;
import com.ns.warlock.Message;
import com.ns.warlock.Setting;
import com.ns.warlock.service.CacheService;
import com.ns.warlock.service.FileService;
import com.ns.warlock.util.SettingUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller("settingController")
@RequestMapping("/admin/setting")
public class SettingController extends BaseController {

    @Resource(name = "fileServiceImpl")
    private FileService fileService;
    @Resource(name = "cacheServiceImpl")
    private CacheService cacheService;

    /**
     * 编辑
     * @return
     */
    @GetMapping(value = "/edit")
    public String edit () {
        return "/admin/setting/edit";
    }

    @PostMapping(value = "/update")
    public String update (Setting setting, MultipartFile titleImageFile,
                          MultipartFile promptImageFile,
                          RedirectAttributes redirectAttributes) {

        Setting srcSetting = SettingUtils.get();

        //提交titleImageFile
        if (titleImageFile != null && !titleImageFile.isEmpty()) {
            if (!fileService.isValid(FileType.image, titleImageFile)) {
                addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                return "redirect:edit.jhtml";
            }
            String titleImageFilePath = fileService.uploadLocal(FileInfo.FileType.image, titleImageFile);
            setting.setTitleImagePath(titleImageFilePath);
        } else {
            setting.setTitleImagePath(srcSetting.getTitleImagePath());
        }

        //提交promptImageFile
        if (promptImageFile != null && !promptImageFile.isEmpty()) {
            if (!fileService.isValid(FileType.image, promptImageFile)) {
                addFlashMessage(redirectAttributes, Message.error("admin.upload.invalid"));
                return "redirect:edit.jhtml";
            }
            String promptImageFilePath = fileService.uploadLocal(FileInfo.FileType.image, promptImageFile);
            setting.setPromptImagePath(promptImageFilePath);
        } else {
            setting.setPromptImagePath(srcSetting.getPromptImagePath());
        }

        SettingUtils.set(setting);
        cacheService.clear();

        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:edit.jhtml";
    }
}
