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
                <h2>${message("admin.deposit.list")}</h2>
                <ol class="breadcrumb">
                    <li>
                    ${message("admin.title.deposit")}
                    </li>
                    <li class="active">
                        <strong>${message("admin.deposit.add")}</strong>
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
                                <input hidden id="memberId" name="memberId" value="${memberId}" />

                                [#--<div class="form-group">--]
                                    [#--<label class="col-sm-2 control-label">${message("admin.deposit.memberPhone")}</label>--]
                                    [#--<div class="col-sm-4"><input id="memberPhone" name="memberPhone" type="text" class="form-control" required/></div>--]
                                [#--</div>--]

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.deposit.credit")}</label>
                                    <div class="col-sm-4"><input id="credit" name="credit" type="number" class="form-control" required/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.deposit.memo")}</label>
                                    <div class="col-sm-4"><input id="memo" name="memo" type="text" class="form-control"/></div>
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
        $("#memberDeposit").addClass("active");

        var $submit = $("#submit");
        var $memberPhone = $("#memberPhone");


        var $inputForm = $("#inputForm");

        $inputForm.validate({
            rules: {
//                memberPhone:{
//                    required:true,
//                    remote: {
//                        url: "member_validate.jhtml",
//                        dateType: "json",
//                        data: { memberPhone: function() { return $memberPhone.val(); }},
//                        cache: false
//                    }
//                },
                credit:{
                    required:true,
                    digits:true
                }
            },
            messages: {
//                memberPhone: {
//                    required: "会员手机号不能为空",
//                    remote: "无法找到该会员，请输入正确的手机号"
//                },
                credit: "充值金额不能为空"
            }
        });

    });
</script>

</body>
</html>
