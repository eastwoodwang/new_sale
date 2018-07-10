<nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <!--
                    <span>
                        <img alt="image" class="img-circle" src="${base}/resources/admin/img/ic_logo.png" />
                    </span>
                    -->
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs"> <strong class="font-bold">${message("common.admin.title")}</strong></span>
                            <span class="text-muted text-xs block">
                                [@shiro.principal property="name" /]
                                    <!-- <b class="caret"></b> -->
                            </span>
                        </span>
                    </a>
                    <!--
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">

                        <li>
                            <a href="../common/profile.jhtml">${message("common.operate.change.password")}</a>
                        </li>

                        <li class="divider"></li>
                        <li><a href="../logout.jsp" target="_top">${message("common.logout")}</a></li>

                    </ul>
                    -->
                </div>
                <div class="logo-element">
                    ${message("common.company.name")}
                </div>
            </li>
            <!-- dashboard -->
            <li id="dashboard">
                <a href="../common/main.jhtml"><i class="fa fa-th-large"></i> <span class="nav-label">${message("admin.title.dashboard")}</span></a>
            </li>


            <!-- 商品管理模块 -->
            [#assign productPermission = false ]
            [#list ["admin:product"] as permission]
                [@shiro.hasPermission name = permission]
                    [#assign productPermission =  true]
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
            [#if productPermission == true ]
                <li id="product">
                    <a href="../product/list.jhtml"><i class="fa fa-shopping-cart"></i> <span class="nav-label">${message("admin.title.product")}</span></a>
                </li>
            [/#if]


            <!-- 订单管理模块 -->
            [#assign orderPermission = false ]
            [#list ["admin:member", "admin:customer"] as permission]
                [@shiro.hasPermission name = permission]
                    [#assign orderPermission =  true]
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
            [#if orderPermission == true ]
                <li id="order">
                    <a href="../order/list.jhtml"><i class="fa fa-files-o"></i> <span class="nav-label">${message("admin.title.order")}</span></a>
                </li>
            [/#if]


            <!-- 会员管理模块 -->
            [#assign memberPermission = false ]
            [#list ["admin:member", "admin:memberLevel", "admin:memberDeposit"] as permission]
                [@shiro.hasPermission name = permission]
                    [#assign memberPermission =  true]
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
            [#if memberPermission == true ]
                <li id="member">
                    <a href="#"><i class="fa fa-user"></i> <span class="nav-label">${message("admin.title.member")}</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                        <!-- 会员列表 -->
                        [@shiro.hasPermission name="admin:member"]
                            <li id="memberList">
                                <a href="../member/list.jhtml">${message("admin.member.list")}</a>
                            </li>
                        [/@shiro.hasPermission]

                        <!-- 会员等级 -->
                        [@shiro.hasPermission name="admin:memberLevel"]
                            <li id="memberLevel">
                                <a href="../member_level/list.jhtml">${message("admin.level.list")}</a>
                            </li>
                        [/@shiro.hasPermission]

                        <!-- 会员预充值 -->
                        [@shiro.hasPermission name="admin:memberDeposit"]
                            <li id="memberDeposit">
                                <a href="../member_deposit/list.jhtml">${message("admin.deposit.list")}</a>
                            </li>
                        [/@shiro.hasPermission]
                    </ul>
                </li>
            [/#if]


            <!-- 团队管理模块 -->
            [#assign groupPermission = false ]
            [#list ["admin:group", "admin:groupLevel"] as permission]
                [@shiro.hasPermission name = permission]
                    [#assign groupPermission =  true]
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
            [#if groupPermission == true ]
                <li id="group">
                    <a href="#"><i class="fa fa-users"></i> <span class="nav-label">${message("admin.title.group")}</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                        <!-- 团队列表 -->
                        [@shiro.hasPermission name="admin:group"]
                            <li id="groupList">
                                <a href="../group/list.jhtml">${message("admin.group.list")}</a>
                            </li>
                        [/@shiro.hasPermission]

                        <!-- 团队等级列表 -->
                        [@shiro.hasPermission name="admin:groupLevel"]
                            <li id="groupLevel">
                                <a href="../group_level/list.jhtml">${message("admin.group.level")}</a>
                            </li>
                        [/@shiro.hasPermission]
                    </ul>

                </li>
            [/#if]

            <!-- 配置管理模块 -->
            [#assign systemPermission = false ]
            [#list ["admin:profile", "admin:setting"] as permission]
                [@shiro.hasPermission name = permission]
                    [#assign systemPermission =  true]
                    [#break /]
                [/@shiro.hasPermission]
            [/#list]
            [#if systemPermission == true ]
                <li id="system">
                    <a href="#"><i class="fa fa-gears"></i> <span class="nav-label">${message("admin.title.system")}</span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level collapse">
                        <!-- 修改密码 -->
                        [@shiro.hasPermission name="admin:profile"]
                            <li id="profile">
                                <a href="../common/profile.jhtml">${message("admin.change.password")}</a>
                            </li>
                        [/@shiro.hasPermission]
                        <!-- 系统设置 -->
                        [@shiro.hasPermission name="admin:setting"]
                            <li id="setting">
                                <a href="../setting/edit.jhtml">${message("admin.system.setting")}</a>
                            </li>
                        [/@shiro.hasPermission]
                    </ul>
                </li>
            [/#if]
        </ul>
    </div>
</nav>


<script type="text/javascript">
    window.onload = function(){

        $(function(){

            var menu = $(".navbar-top-links li");

            $(menu[0]).after('<li><a href="javascript:location.reload();"><i class="fa fa-refresh"></i>刷新</a></li>');
        });
    }
</script>