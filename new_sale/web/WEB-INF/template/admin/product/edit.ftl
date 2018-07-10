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
                <h2>${message("admin.title.product")}</h2>
                <ol class="breadcrumb">
                    <li class="active">
                        <strong>${message("admin.product.edit")}</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">

            <form id="inputForm" method="post" enctype="multipart/form-data" class="form-horizontal" action="update.jhtml">
                <input type="hidden" name="id" value="${productDTO.id}" />
                <div class="row">
                    <div class="col-lg-12">
                        <div class="tabs-container">

                            <ul class="nav nav-tabs">
                                <li class="active"><a data-toggle="tab" href="#tab-1">${message("admin.product.info")}</a></li>
                            </ul>


                            <div class="tab-content">
                                <!-- 商品基本信息 -->
                                <div id="tab-1" class="tab-pane active">
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">${message("admin.product.name")}</label>
                                            <div class="col-sm-4"><input id="name" name="name" value="${productDTO.name}" type="text" class="form-control" /></div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">${message("admin.product.price")}</label>
                                            <div class="col-sm-4"><input id="price" name="price" value="${productDTO.price}" type="text" class="form-control" /></div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">${message("admin.product.memberPrice")}</label>
                                            <div class="col-sm-4"><input id="memberPrice" name="memberPrice" value="${productDTO.memberPrice}"  type="text" class="form-control"  /></div>
                                        </div>


                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">${message("admin.product.stock")}</label>
                                            <div class="col-sm-4"><input id="stock" name="stock" type="text" value="${productDTO.stock}" class="form-control" /></div>
                                        </div>


                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">${message("admin.product.marketable")}</label>
                                            <div class="col-sm-4">
                                                <select class="form-control" name= "marketable" id="marketable">
                                                    <option value="1" [#if productDTO.marketable = '1']selected="selected"[/#if]>${message("common.market")}</option>
                                                    <option value="2" [#if productDTO.marketable = '2']selected="selected"[/#if]>${message("common.inmarket")}</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-md-12">
                                            <div class="">
                                                <button class="btn btn-primary" type="button" id="productImageAdd">
                                                    <i class="fa fa-plus">&nbsp;${message("admin.product.image.add")}</i>
                                                </button>
                                            </div>

                                            <div class="table-responsive">
                                                <table class="table table-bordered table-stripped" id="productImageTable">
                                                    <tr>
                                                        <th class="col-lg-8">${message("admin.product.imageFile")}</th>
                                                        <th class="col-lg-2">${message("common.order")}</th>
                                                        <th class="col-lg-2">${message("common.operation")}</th>
                                                    </tr>
                                                    [#list productDTO.productImages as imageItem]
                                                        <td class="form-inline">
                                                            <input type="hidden" name="productImages[${imageItem_index}].source" value="${imageItem.source}" />
                                                            <input type="file" name="productImages[${imageItem_index}].file" class="inline" />
                                                            <a href="${imageItem.large}" target="_blank" class="inline">[${message("common.view")}]</a>
                                                        </td>
                                                        <td><input type="text" name="productImages[${imageItem_index}].imageOrder" value="${imageItem.imageOrder}" class="text productImageOrder" /></td>
                                                        <td>
                                                            <a href="deteleImage.html?id=${imageItem.id}">${message("common.delete")}</a>
                                                        </td>
                                                    [/#list]
                                                </table>
                                            </div>
                                        </div>

                                        <div class="col-md-8">
                                            <div class="hr-line-dashed"></div>
                                            <div class="col-lg-4 text-center">
                                                <button class="btn btn-primary" type="submit">${message("common.submit")}</button>
                                            </div>
                                        </div>
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
        $("#product").addClass("active");

        //商品图片数量 从已有的商品图片开始计算
        var productImageIndex = ${(productDTO.productImages?size)!"0"};

        var $inputForm = $("#inputForm");
        var $productImageAdd = $("#productImageAdd");
        var $productImageTable = $("#productImageTable");
        //删除商品图片
        $productImageTable = $("#productImageTable");

        //增加CLASS类型验证
        $.validator.addClassRules({
            productImageFile: {
                required: true,
                extension: "${setting.uploadImageExtension}"
            },
            productImageOrder: {
                required: true,
                digits: true
            }
        });

        //表单验证
        $inputForm.validate({
            rules: {
                name:{
                    required: true
                },
                price:{
                    required: true,
                    min: 0,
                    decimal: {
                        integer: 12,
                        fraction: ${setting.priceScale}
                    }
                },
                memberPrice:{
                    min: 0,
                    decimal: {
                        integer: 12,
                        fraction: ${setting.priceScale}
                    }
                },
                //weight: "digits",
                stock: "digits"
            },
            messages: {
                name: "请填写正确的商品名称",
                price: "请填写正确的商品价格",
                memberPrice: "请填写正确的商品会员价格",
                stock: "请填写正确的商品库存"
            }
        });

        //对于新增的商品参数 需要使用deletegate来绑定 如果是已经存在的就用on 如: on('click', 'td a', function() {
        $productImageTable.delegate("a.deleteProductImage", "click", function() {
            var $this = $(this);
            $this.closest("tr").remove();
        });

        //点击新增图片
        $productImageAdd.click(function() {
        [@compress single_line = true]
            var trHtml = '<tr>
                    <td> <input type="file" name="productImages[' + productImageIndex + '].file" class="productImageFile"  \/> <\/td>
        <td> <input type="text" name="productImages[' + productImageIndex + '].imageOrder" class="text productImageOrder" \/> <\/td>
        <td> <a href="javascript:;" class="deleteProductImage">${message("common.delete")}<\/a> <\/td>
        <\/tr>';
        [/@compress]
            $productImageTable.append(trHtml);
            productImageIndex ++;
        });

    });
</script>
</body>
</html>
