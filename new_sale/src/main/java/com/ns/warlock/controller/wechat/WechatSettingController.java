package com.ns.warlock.controller.wechat;

import com.ns.warlock.Setting;
import com.ns.warlock.util.SettingUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechat/setting")
@CrossOrigin
public class WechatSettingController extends BaseController {

    /**
     * 获取系统配置
     * @return
     */
    @PostMapping(value = "/setting")
    @ApiOperation(value = "系统设置", notes = "获取用户的系统设置")
    public @ResponseBody
    Setting getSystemSetting() {
        return SettingUtils.get();
    }
}
