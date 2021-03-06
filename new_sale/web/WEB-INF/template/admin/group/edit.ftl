[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>${message("admin.main.title")}</title>

    <link href="${base}/resources/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="${base}/resources/admin/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${base}/resources/admin/css/plugins/iCheck/custom.css" rel="stylesheet">

    <link href="${base}/resources/admin/css/animate.css" rel="stylesheet">
    <link href="${base}/resources/admin/css/style.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">

[#include "/admin/common/navbar.ftl"]

    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">${message("common.admin.title")}</span>
                    </li>

                    <li>
                        <a href="../logout.jsp" target="_top"><i class="fa fa-sign-out"></i>${message("common.logout")}</a>
                    </li>
                </ul>
            </nav>
        </div>


        <!--  content start  -->

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>${message("admin.member.list")}</h2>
                <ol class="breadcrumb">
                    <li>
                    ${message("admin.title.member")}
                    </li>
                    <li class="active">
                        <strong>${message("admin.member.edit")}</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">

            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-content">
                            <form id="inputForm" method="post" class="form-horizontal" action="update.jhtml">
                                <input type="text" hidden="hidden" name="id", value="${memberDTO.id}">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.member.nickname")}</label>
                                    <div class="col-sm-4">${memberDTO.nickname}</div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.member.level")}</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" name="memberLevel">
                                            [#list levelList as levelItem]
                                                <option value="${levelItem.id}"[#if levelItem.id == memberDTO.memberLevel] selected="selected"[/#if]>${levelItem.levelName}</option>
                                            [/#list]
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.member.status")}</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" name="status">
                                            <option value="">${message("common.choose")}</option>
                                            <option value="0" [#if memberDTO.status == 0] selected="selected"[/#if]>${message("common.locked")}</option>
                                            <option value="1" [#if memberDTO.status == 1] selected="selected"[/#if]>${message("common.actived")}</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-4 text-center">
                                        <button class="btn btn-primary" type="submit">提交</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--  content end  -->
        <div class="footer">
            <div class="text-center">
                <strong>${message("common.company.copyright")}</strong>
            </div>
        </div>
    </div>

</div>



<!-- Mainly scripts -->
<script src="${base}/resources/admin/js/jquery-2.1.1.js"></script>
<script src="${base}/resources/admin/js/bootstrap.min.js"></script>

<!-- jQuery UI -->
<script src="${base}/resources/admin/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${base}/resources/admin/js/jquery.validate.js"></script>

<!-- Custom and plugin javascript -->
<script src="${base}/resources/admin/js/inspinia.js"></script>
<script src="${base}/resources/admin/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${base}/resources/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="${base}/resources/admin/js/plugins/iCheck/icheck.min.js"></script>

<script>
    $(document).ready(function(){

        //设置选中
        $("#group").addClass("active").children("ul").addClass("collapse in");
        $("#groupList").addClass("active");

        var $inputForm = $("#inputForm");

        $inputForm.validate({
            rules: {
                status: {
                    required:true
                }
            },
            messages: {
                status: "请选择会员账号状态"
            }
        });


        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

    });
</script>

</body>
</html>
