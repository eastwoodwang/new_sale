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

    <!-- Sweet Alert -->
    <link href="${base}/resources/admin/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
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
                <h2>${message("admin.member.level")}</h2>
                <ol class="breadcrumb">
                    <li>
                    ${message("admin.title.member")}
                    </li>
                    <li class="active">
                        <strong>${message("admin.member.level.create")}</strong>
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
                            <form id="inputForm" method="post" class="form-horizontal" action="save.jhtml">

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.level.levelName")}</label>
                                    <div class="col-sm-4"><input id="levelName" name="levelName" type="text" class="form-control" required/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.level.minRange")}</label>
                                    <div class="col-sm-4"><input id="minRange" name="minRange" type="number" class="form-control" required/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.level.maxRange")}</label>
                                    <div class="col-sm-4"><input id="maxRange" name="maxRange" type="number" class="form-control" required/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.level.priority")}</label>
                                    <div class="col-sm-4"><input id="priority" name="priority" type="number" class="form-control" required/></div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-4 text-center">
                                        <button class="btn btn-primary" id="submit" type="submit">提交</button>
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

<!-- Sweet alert -->
<script src="${base}/resources/admin/js/plugins/sweetalert/sweetalert.min.js"></script>

<script>
    $(document).ready(function(){

        //设置选中
        $("#member").addClass("active").children("ul").addClass("collapse in");
        $("#memberLevel").addClass("active");


        var $submit = $("#submit");
        var $minRange = $("#minRange");
        var $maxRange = $("#maxRange");

        var $inputForm = $("#inputForm");

        $inputForm.validate({
            rules: {
                levelName:{
                    required:true
                },
                minRange:{
                    required:true,
                    digits:true,
                    min:0,
                    remote: {
                        url: "range_validate.jhtml",
                        dataType: "json",
                        data: { range: function() { return $minRange.val(); }},
                        cache: false
                    }
                },
                maxRange:{
                    required:true,
                    digits:true,
                    min:0,
                    remote: {
                        url: "range_validate.jhtml",
                        dataType: "json",
                        data: { range: function() { return $maxRange.val(); }},
                        cache: false
                    }
                },
                priority:{
                    required:true,
                    digits:true
                }
            },
            messages: {
                levelName: "会员等级名不能为空",
                minRange: "最低消费值不能为空或已经存在于其它范围内",
                maxRange: "最高消费值不能为空或已经存在于其它范围内",
                priority: "请设置会员等级排名"
            }
        });


        //提交时比较大小
        $submit.click(function(){
            //比较最大值和最小值的
            var maxRange = parseFloat($maxRange.val());
            var minRange = parseFloat($minRange.val());
            if ( maxRange < minRange) {
                swal({
                    title: "消费最小值不能高于最大值！",
                    text: "2秒后自动关闭。",
                    type: "warning",
                    timer: 2000,
                    showConfirmButton: false
                });
                return false;
            }
        });

    });
</script>

</body>
</html>
