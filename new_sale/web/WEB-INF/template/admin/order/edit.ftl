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
                <h2>${message("admin.title.order")}</h2>
                <ol class="breadcrumb">
                    <li class="active">
                        <strong>${message("admin.order.edit")}</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">

            <form id="inputForm" method="post" class="form-horizontal" action="update.jhtml">
                <input type="hidden" name="id"  value="${order.id}" />
                <div class="row">
                    <div class="col-lg-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.sn")}</label>
                                    <div class="col-sm-4"><input value="${order.orderSn}" type="text" class="form-control" disabled=""/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.status")}</label>
                                    <div class="col-sm-4">

                                    [#if order.orderStatus == 0]
                                        <input value="${message("admin.order.OrderStatus.0")}" type="text" class="form-control" disabled=""/>
                                    [/#if]
                                    [#if order.orderStatus == 1]
                                        <input value="${message("admin.order.OrderStatus.1")}" type="text" class="form-control" disabled=""/>
                                    [/#if]
                                    [#if order.orderStatus == 2]
                                        <input value="${message("admin.order.OrderStatus.2")}" type="text" class="form-control" disabled=""/>
                                    [/#if]
                                    [#if order.orderStatus == 3]
                                        <input value="${message("admin.order.OrderStatus.3")}" type="text" class="form-control" disabled=""/>
                                    [/#if]
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.createDate")}</label>
                                    <div class="col-sm-4">
                                        <input id="createDate" name="createDate" value="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}" type="text" class="form-control" disabled=""/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.totalAmount")}</label>
                                    <div class="col-sm-4"><input id="totalAmount" name="totalAmount" type="text" value="${order.totalAmount}" class="form-control" disabled=""/></div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.payDate")}</label>
                                    <div class="col-sm-4">
                                        <input id="payDate" name="payDate" value="${order.payDate?string("yyyy-MM-dd HH:mm:ss")}" type="text" class="form-control" disabled=""/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.shipping.sn")}</label>
                                    <div class="col-sm-4">
                                        <input id="shippingSn" name="shippingSn" value="${order.shippingSn}" type="text" class="form-control" required/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">${message("admin.order.shipping.sn")}</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" name= "shippingStatus" id="shippingStatus">
                                            <option value="">${message("common.select")}</option>
                                            <option value="0" [#if order.shippingStatus == 0] selected="selected" [/#if]>${message("admin.order.ShippingStatus.0")}</option>
                                            <option value="1" [#if order.shippingStatus == 1] selected="selected" [/#if]>${message("admin.order.ShippingStatus.1")}</option>
                                            <option value="2" [#if order.shippingStatus == 2] selected="selected" [/#if]>${message("admin.order.ShippingStatus.2")}</option>
                                        </select>
                                    </div>
                                </div>


                                <div class="table-responsive">
                                    <table class="table table-bordered table-stripped" id="orderItemTable">
                                        <tr>
                                            <th class="col-lg-3">${message("admin.product.sn")}</th>
                                            <th class="col-lg-2">${message("admin.product.name")}</th>
                                            <th class="col-lg-2">${message("admin.product.quantity")}</th>
                                            <th class="col-lg-2">${message("admin.product.price")}</th>
                                            <th class="col-lg-2">${message("admin.product.total.price")}</th>
                                        </tr>
                                        [#list orderItems as orderItem]
                                        <tr>
                                            <td>${orderItem.productSn}</td>
                                            <td>${orderItem.name}</td>
                                            <td>${orderItem.quantity}</td>
                                            <td>${orderItem.price} 元</td>
                                            <td>${orderItem.itemTotalPrice} 元</td>
                                        </tr>
                                        [/#list]
                                    </table>
                                </div>


                                <div class="form-group">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-8">
                                        <button class="btn btn-primary" type="submit">${message("common.submit")}</button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </form>
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
    $().ready(function(){
        //设置选中
        $("#order").addClass("active");

        //表单验证
        $inputForm.validate({
            rules: {
                shippingSn:{
                    required: true
                },
                shippingStatus: {
                    required: true
                }
            },
            messages: {
                shippingSn: "请填写快递单号",
                shippingStatus: "请选择快递状态"
            }
        });

    });
</script>
</body>
</html>
