package top.zway.fic.user.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zway.fic.base.entity.doo.UserInfoDO;
import top.zway.fic.base.result.R;
import top.zway.fic.user.service.UserInfoService;

import java.util.HashMap;

/**
 * @author ZZJ
 */
@RestController
@Api("rpc用户信息api")
@RequiredArgsConstructor
public class UserInfoRpcController {
    private final UserInfoService userInfoService;

    @PostMapping("/rpc/userinfo/list")
    public R<HashMap<Long, UserInfoDO>> getUserInfoDoByList(@RequestParam("userIds") Long[] userIds) {
        HashMap<Long, UserInfoDO> ret = new HashMap<>((int) (userIds.length / 0.75) + 1);
        for (Long userId : userIds) {
            UserInfoDO userInfoDo = userInfoService.getUserInfoDo(userId);
            ret.put(userId,userInfoDo);
        }
        return R.success(ret);
    }
}
