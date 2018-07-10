
$(document).ready(function () {

    var $selectAll = $("#selectAll");
    var $deleteButton = $("#deleteButton");
    var $ids = $("#dataTable input[name='ids']");
    var $refreshButton = $("#refreshButton");

    // 刷新
    $refreshButton.click( function() {
        location.reload(true);
        return false;
    });

    //全选
    $selectAll.click(function() {
        var $this = $(this);
        var $enabledIds = $("#dataTable input[name='ids']:enabled");
        if ($this.prop("checked")) {
            $enabledIds.prop("checked", true);
            if ($enabledIds.filter(":checked").size() > 0) {
                $deleteButton.removeClass("disabled");
            } else {
                $deleteButton.addClass("disabled");
            }
        } else {
            $enabledIds.prop("checked", false);
            $deleteButton.addClass("disabled");
        }
    });

    //单选
    $ids.click( function() {
        var $this = $(this);
        if ($this.prop("checked")) {
            $deleteButton.removeClass("disabled");
        } else {
            if ($("#dataTable input[name='ids']:enabled:checked").size() > 0) {
                $deleteButton.removeClass("disabled");
            } else {
                $selectAll.prop("checked", false);
                $deleteButton.addClass("disabled");
            }
        }
    });

    //删除
    $deleteButton.click( function () {
        var $this = $(this);
        if ($this.hasClass("disabled")) {
            return false;
        }

        var $checkedIds = $("#dataTable input[name='ids']:enabled:checked");
        swal({
            title: "数据删除",
            text: "删除的数据不可恢复,您确定要删除选中的数据吗?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            cancelButtonText: "取消",
            closeOnConfirm: false
        }, function(){
            $.ajax({
                url: "delete.jhtml",
                type: "POST",
                data: $checkedIds.serialize(),
                dataType: "json",
                cache: false,
                success: function(message) {
                    if (message.type == "success") {
                        swal("删除成功", "选中的数据已被删除", "success");
                        setTimeout(function() {
                            location.reload(true);
                        }, 1500);

                        $deleteButton.addClass("disabled");
                        $selectAll.prop("checked", false);
                        $checkedIds.prop("checked", false);

                    } else if (message.type == "error") {
                        swal("无法删除", message.content, "error");
                    }
                }
            });
        });
    });


    <!-- 初始化dataTable -->
    $('#dataTable').DataTable({
        "paging":   true,
        "ordering": false,
        "info":     true,
        "lengthMenu": [10, 20, 50, 100],
        "language": {
            "lengthMenu": "每页显示 _MENU_ 记录",
            "zeroRecords": "无任何数据",
            "info": "第 _START_ - _END_ 条 / 共 _TOTAL_ 条数据",
            "infoEmpty": "无任何数据",
            "infoFiltered": "( _MAX_ 条数据过滤结果)",
            "search": "查询:",
            "paginate": {
                "previous": "上一页",
                "next": "下一页"
            }
        }
    });

});