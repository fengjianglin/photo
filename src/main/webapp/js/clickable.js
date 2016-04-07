
var ImagePosition = function () {
    this.photo_id = 0;
    this.x_px = 0;
    this.y_px = 0;
    this.x_page = 0;
    this.y_page = 0;
}


$(function () {

    window.popover = $("#popover");
    window.popover.prompt = $("#prompt");
    window.popover.p = new ImagePosition();

    $("body").bind('click', function (e) {
        window.popover.hide();
    });

    $("img.clickable").bind('click', function (e) {
        with (window.popover.p) {
            photo_id = $(this).attr("photo_id");
            x_page = e.pageX;
            y_page = e.pageY;
            x_px = e.pageX - parseInt($(this).offset().left);
            y_px = e.pageY - parseInt($(this).offset().top);
            window.popover.css({top: y_page, left: x_page});
        }
        window.popover.show();
        window.popover.prompt.focus();
        e.stopPropagation();
    });

    window.popover.bind('click', function (e) {
        e.stopPropagation();
    });

    window.popover.prompt.bind('keypress', function (event) {
        if (event.keyCode == "13") {
            submit();
        }
    });

    $("span#submit").bind('click', function (e) {
        submit();
    });

    function submit() {
        with (window.popover) {
            var text = $.trim(prompt.val());
            if (text.length == 0) {
                alert("内容不能为空！");
            } else {
                $.post("/photo/prompt", {
                    photo_id: p.photo_id,
                    x: p.x_px,
                    y: p.y_px,
                    text: text
                }, function (result) {
                    prompt.val("");
                    hide();
                });
            }
        }
    }
})

