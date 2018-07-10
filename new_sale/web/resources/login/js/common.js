(function($) {

    $.toast = function() {
        //定义一个message对象
        var message = {};

        if ($.isPlainObject(arguments[0])) {
            message = arguments[0]; //第一个参数给message
        } else if (typeof arguments[0] === "string" && typeof arguments[1] === "string") {
            message.type = arguments[0]
            message.content = arguments[1];
        } else {
            return false;
        }

        if (message.type == null || message.content == null) {
            return false;
        }

        toastr.options = {
            "positionClass": "toast-top-center",
        }

        if (message.type == "success") {
            toastr.success('', message.content);
        } else if (message.type == "error") {
            toastr.error('', message.content);
        }

    };

})(jQuery);

