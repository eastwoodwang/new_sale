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
                <h2>${message("admin.title.system")}</h2>
                <ol class="breadcrumb">
                    <li>
                    ${message("admin.system.setting")}
                    </li>
                    <li class="active">
                        <strong>${message("admin.system.setting")}</strong>
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
                            <form id="inputForm" method="post" enctype="multipart/form-data" class="form-horizontal" action="update.jhtml">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.siteName")}</label>
                                    <div class="col-sm-4">
                                        <input id="siteName" name="siteName" value="${setting.siteName}" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.siteUrl")}</label>
                                    <div class="col-sm-4">
                                        <input id="siteUrl" name="siteUrl" value="${setting.siteUrl}" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("Setting.largeProductImage")}</label>
                                    <div class="col-sm-1 control-label" >${message("Setting.largeProductImage.width")}</div>
                                    <div class="col-sm-1">
                                        <input id="largeProductImageWidth" maxlength="9" name="largeProductImageWidth" value="${setting.largeProductImageWidth}" type="text" class="form-control" required/>
                                    </div>
                                    <div class="col-sm-1  control-label">${message("Setting.largeProductImage.height")}</div>
                                    <div class="col-sm-1">
                                        <input id="largeProductImageHeight" maxlength="9" name="largeProductImageHeight"  value="${setting.largeProductImageHeight}" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.mediumProductImage")}</label>
                                    <div class="col-sm-1 control-label">${message("Setting.mediumProductImage.width")}</div>
                                    <div class="col-sm-1">
                                        <input id="mediumProductImageWidth" maxlength="9" name="mediumProductImageWidth" value="${setting.mediumProductImageWidth}" type="text" class="form-control" required/>
                                    </div>
                                    <div class="col-sm-1 control-label">${message("Setting.mediumProductImage.height")}</div>
                                    <div class="col-sm-1">
                                        <input id="mediumProductImageHeight" maxlength="9" name="mediumProductImageHeight" value="${setting.mediumProductImageHeight}" type="text" class="form-control" required/>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.thumbnailProductImage")}</label>
                                    <div class="col-sm-1 control-label">${message("Setting.thumbnailProductImage.width")}</div>
                                    <div class="col-sm-1">
                                        <input id="thumbnailProductImageWidth" maxlength="9" name="thumbnailProductImageWidth" value="${setting.thumbnailProductImageWidth}" type="text" class="form-control" required/>
                                    </div>
                                    <div class="col-sm-1 control-label">${message("Setting.thumbnailProductImage.height")}</div>
                                    <div class="col-sm-1">
                                        <input id="thumbnailProductImageHeight" maxlength="9" name="thumbnailProductImageHeight" value="${setting.thumbnailProductImageHeight}" type="text" class="form-control" required/>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.priceScale")}</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" name="priceScale">
                                            <option value="0"[#if setting.priceScale == 0] selected="selected"[/#if]>${message("admin.setting.priceScale0")}</option>
                                            <option value="1"[#if setting.priceScale == 1] selected="selected"[/#if]>${message("admin.setting.priceScale1")}</option>
                                            <option value="2"[#if setting.priceScale == 2] selected="selected"[/#if]>${message("admin.setting.priceScale2")}</option>
                                            <option value="3"[#if setting.priceScale == 3] selected="selected"[/#if]>${message("admin.setting.priceScale3")}</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.uploadImageExtension")}</label>
                                    <div class="col-sm-4">
                                        <input id="uploadImageExtension" type="text" name="uploadImageExtension" value="${setting.uploadImageExtension}" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.imageUploadPath")}</label>
                                    <div class="col-sm-4">
                                        <input id="imageUploadPath" type="text" name="imageUploadPath" value="${setting.imageUploadPath}" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.titleImagePath")}</label>
                                    <div class="col-sm-4">
                                        <input id="titleImageFile" type="file" name="titleImageFile" value="${setting.titleImagePath}" />
                                    </div>
                                    [#if setting.titleImagePath != null]
                                        <div class="col-sm-1 control-label">
                                            <a href="${base}${setting.titleImagePath}" target="_blank">${message("common.view")}</a>
                                        </div>
                                    [/#if]
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label required">${message("Setting.promptImagePath")}</label>
                                    <div class="col-sm-2">
                                        <input id="promptImageFile" type="file" name="promptImageFile" value="${setting.promptImagePath}" />
                                    </div>
                                    [#if setting.promptImagePath != null]
                                        <div class="col-sm-1">
                                            <a href="${base}${setting.promptImagePath}" target="_blank">${message("common.view")}</a>
                                        </div>
                                    [/#if]
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
        $("#system").addClass("active").children("ul").addClass("collapse in");
        $("#setting").addClass("active");

        var $inputForm = $("#inputForm");

        [@flash_message /]

        // 表单验证
        $inputForm.validate({
            rules: {
                siteName: "required",
                siteUrl: "required",
                largeProductImageWidth: {
                    required: true,
                    integer: true,
                    min: 1
                },
                largeProductImageHeight: {
                    required: true,
                    integer: true,
                    min: 1
                },
                mediumProductImageWidth: {
                    required: true,
                    integer: true,
                    min: 1
                },
                mediumProductImageHeight: {
                    required: true,
                    integer: true,
                    min: 1
                },
                thumbnailProductImageWidth: {
                    required: true,
                    integer: true,
                    min: 1
                },
                thumbnailProductImageHeight: {
                    required: true,
                    integer: true,
                    min: 1
                }
            }
        });

    });
</script>

</body>
</html>
