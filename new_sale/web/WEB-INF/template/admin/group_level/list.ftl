[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>${message("admin.member.level")}</title>

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
                <h2>${message("admin.group.list")}</h2>
                <ol class="breadcrumb">
                    <li>
                    ${message("admin.title.group")}
                    </li>
                    <li class="active">
                        <strong>${message("admin.group.level")}</strong>
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
                                <button class="btn btn-primary" type="button" id="createNew" onclick="location.href='../group_level/add.jhtml'">
                                    <i class="fa fa-plus">&nbsp;${message("common.add")}</i>
                                </button>
                                <button class="btn btn-primary disabled" type="button" id="deleteButton">
                                    <i class="fa fa-trash">&nbsp;${message("common.delete")}</i>
                                </button>
                                <button id="refreshButton" class="btn btn-primary " type="button" onclick="location.reload(true);">
                                    <i class="fa fa-refresh">&nbsp;${message("common.fresh")}</i>
                                </button>
                            </div>
                            <table class="table table-striped table-bordered table-hover " id="dataTable" >
                                <thead>
                                <tr>
                                    <th class="check">
                                        <input type="checkbox" id="selectAll" />
                                    </th>
                                    <th>${message("admin.group.levelName")}</th>
                                    <th>${message("admin.group.minRange")}</th>
                                    <th>${message("admin.group.maxRange")}</th>
                                    <th>${message("admin.group.discountRate")}</th>
                                    <th>${message("admin.group.priority")}</th>
                                    <th>${message("common.operation")}</th>
                                </tr>
                                </thead>
                                <tbody>
                                [#list list as groupLevelDTO]
                                <tr class="gradeA">
                                    <td>
                                        <input type="checkbox" name="ids" value="${groupLevelDTO.id}" />
                                    </td>
                                    <td>${groupLevelDTO.groupLevelName}</td>
                                    <td>${groupLevelDTO.minRange} 元</td>
                                    <td>${groupLevelDTO.maxRange} 元</td>
                                    <td>${groupLevelDTO.discountRate} %</td>
                                    <td>${groupLevelDTO.priority}</td>
                                    <td>
                                        <a href="edit.jhtml?id=${groupLevelDTO.id}">[${message("common.edit")}]</a>
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
        $("#group").addClass("active").children("ul").addClass("collapse in");
        $("#groupLevel").addClass("active");
    });
</script>

</body>
</html>
