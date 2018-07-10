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

    <link href="${base}/resources/admin/css/plugins/dataTables/datatables.min.css" rel="stylesheet">

    <link href="${base}/resources/admin/css/animate.css" rel="stylesheet">
    <link href="${base}/resources/admin/css/style.css" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="${base}/resources/admin/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">

[#include "/admin/common/navbar.ftl"]

    <div id="page-wrapper" class="gray-bg">
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
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>${message("admin.title.product")}</h2>
                <ol class="breadcrumb">
                    <li class="active">
                        <strong>${message("admin.title.product")}</strong>
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
                            <div class="">
                                <button class="btn btn-primary" type="button" id="createNew" onclick="location.href='../product/add.jhtml'">
                                    <i class="fa fa-plus">&nbsp;${message("common.add")}</i>
                                </button>
                                <button class="btn btn-primary disabled" type="button" id="deleteButton">
                                    <i class="fa fa-trash">&nbsp;${message("common.delete")}</i>
                                </button>
                                <button id="refreshButton" class="btn btn-primary" type="button" onclick="location.reload(true);">
                                    <i class="fa fa-refresh">&nbsp;${message("common.fresh")}</i>
                                </button>
                                <button id="marketableButton" class="btn btn-primary" type="button">
                                    <i class="fa fa-arrow-up">&nbsp;${message("common.market")}</i>
                                </button>
                                <button id="inmarketableButton" class="btn btn-primary" type="button">
                                    <i class="fa fa-arrow-down">&nbsp;${message("common.inmarket")}</i>
                                </button>
                            </div>
                            <table class="table table-striped table-bordered table-hover " id="dataTable" >
                                <thead>
                                <tr>
                                    <th class="check">
                                        <input type="checkbox" id="selectAll" />
                                    </th>
                                    <th>${message("admin.product.sn")}</th>
                                    <th>${message("admin.product.name")}</th>
                                    <th>${message("admin.product.price")}</th>
                                    <th>${message("admin.product.memberPrice")}</th>
                                    <th>${message("admin.product.stock")}</th>
                                    <th>${message("admin.product.allocateStock")}</th>
                                    <th>${message("admin.product.marketable")}</th>
                                    <th>${message("common.operation")}</th>
                                </tr>
                                </thead>
                                <tbody>
                                [#list list as productDTO]
                                <tr class="gradeA">
                                    <td>
                                        <input type="checkbox" name="ids" value="${productDTO.id}" />
                                    </td>
                                    <td>${productDTO.sn}</td>
                                    <td>${productDTO.name}</td>
                                    <td>${productDTO.price} 元</td>
                                    <td>${productDTO.memberPrice} 元</td>
                                    <td>${productDTO.stock}</td>
                                    <td>${productDTO.allocateStock}</td>
                                    <td>
                                     [#if productDTO.marketable == "1" ]
                                        ${message("common.market")}
                                     [#else]
                                        ${message("common.inmarket")}
                                     [/#if]
                                    </td>
                                    <td>
                                        <a href="edit.jhtml?id=${productDTO.id}">[${message("common.edit")}]</a>
                                    </td>
                                </tr>
                                [/#list]
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
<script src="${base}/resources/admin/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${base}/resources/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="${base}/resources/admin/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${base}/resources/admin/js/plugins/dataTables/datatables.min.js"></script>

<!-- Custom and plugin javascript -->
<script src="${base}/resources/admin/js/inspinia.js"></script>
<script src="${base}/resources/admin/js/plugins/pace/pace.min.js"></script>

<!-- Sweet alert -->
<script src="${base}/resources/admin/js/plugins/sweetalert/sweetalert.min.js"></script>

<script src="${base}/resources/admin/js/list.js"></script>


<script>
    $(document).ready(function () {
        //设置选中
        //$("#member").addClass("product").children("ul").addClass("collapse in");
        $("#product").addClass("active");

        $marketableButton = $("#marketableButton");
        $inmarketableButton = $("#inmarketableButton");


        //上架
        $marketableButton.click( function () {
            var $this = $(this);
            if ($this.hasClass("disabled")) {
                return false;
            }

            var $checkedIds = $("#dataTable input[name='ids']:enabled:checked");
            if ($checkedIds.size() > 0) {
                swal({
                    title: "商品上架",
                    text: "您确定要将选中的商品上架吗?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function(){
                    $.ajax({
                        url: "marketable.jhtml",
                        type: "POST",
                        data: $checkedIds.serialize(),
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            if (message.type == "success") {
                                swal("上架成功", "选中的已经上架", "success");
                                setTimeout(function() {
                                    location.reload(true);
                                }, 1500);

                                $marketableButton.addClass("disabled");
                                $selectAll.prop("checked", false);
                                $checkedIds.prop("checked", false);

                            } else if (message.type == "error") {
                                swal("无法上架", message.content, "error");
                            }
                        }
                    });
                });
            }
        });


        //下架
        $inmarketableButton.click( function () {
            var $this = $(this);
            if ($this.hasClass("disabled")) {
                return false;
            }

            var $checkedIds = $("#dataTable input[name='ids']:enabled:checked");
            if ($checkedIds.size() > 0) {
                swal({
                    title: "商品下架",
                    text: "您确定要将选中的商品下架吗?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                }, function(){
                    $.ajax({
                        url: "inmarketable.jhtml",
                        type: "POST",
                        data: $checkedIds.serialize(),
                        dataType: "json",
                        cache: false,
                        success: function(message) {
                            if (message.type == "success") {
                                swal("下架成功", "选中的已经下架", "success");
                                setTimeout(function() {
                                    location.reload(true);
                                }, 1500);

                                $inmarketableButton.addClass("disabled");
                                $selectAll.prop("checked", false);
                                $checkedIds.prop("checked", false);

                            } else if (message.type == "error") {
                                swal("无法下架", message.content, "error");
                            }
                        }
                    });
                });
            }
        });

    });
</script>

</body>
</html>
