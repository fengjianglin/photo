var music_list = [
    "/raw/andxj.m4a",
    "/raw/bbw.m4a",
    "/raw/cqkd.m4a",
    "/raw/ljf.m4a",
    "/raw/lyxz.m4a",
    "/raw/myjxq.m4a",
    "/raw/myq.m4a",
    "/raw/qqdwdbb.mp3",
    "/raw/sb.m4a",
    "/raw/skbljs.m4a",
    "/raw/teqjxq.m4a",
    "/raw/yj.m4a",
    "/raw/yydjtc.m4a",
    "/raw/zals.m4a",
    "/raw/zyxc.m4a"
]

function getRandomMusicUrl() {
    return music_list[Math.floor(Math.random() * music_list.length)];
}

var music = {
    status: 0, // 0:暂停；1:播放
    play_or_pause: function (btn_id, audio_id) {
        var btn = document.getElementById(btn_id);
        var audio = document.getElementById(audio_id);
        if (this.status == 1) {
            this.status = 0;
            $(btn).removeClass('on').addClass('off');
            audio.pause();
        } else {
            this.status = 1;
            $(btn).removeClass('off').addClass('on');
            if (!!!$(audio).attr("src")) {
                $(audio).attr("src", getRandomMusicUrl());
                audio.load();
            }
            audio.play();
        }
    },

    random: function (btn_id, audio_id) {
        var btn = document.getElementById(btn_id);
        var audio = document.getElementById(audio_id);
        if (this.status == 0) {
            this.status = 1;
            $(btn).removeClass('off').addClass('on');
        }
        $(audio).attr("src", getRandomMusicUrl());
        audio.load();
        audio.play();
    }
}

// 图片动画

var gallery = {
    status: 0, // 0:暂停；1:播放
    Zooms: [],
    images: 0,
    screenWidth: 0,
    screenHeight: 0,
    createElement: function (container, type, param) {
        var elem = document.createElement(type);
        for (var key in param) {
            elem.setAttribute(key, param[key]);
        }
        container.appendChild(elem);
        return elem;
    },
    Zoom: function (i) {
        with (gallery) {
            this.span = createElement(document.getElementById("screen"), "span", {
                'class': 'span-slide'
            });
            createElement(this.span, "img", {
                'class': "img-slide",
                'src': images[i].src
            });
            this.N = i;
            this.ratio = images[i].width / images[i].height;
        }
    },

    loop: function () {
        with (this) {
            for (i = 0; i < images.length; i++) {
                Zooms[i].N += 1 / 160;
                h = Math.pow(5, Zooms[i].N % images.length);
                with (Zooms[i].span.style) {
                    left = ((screenWidth - (h * Zooms[i].ratio)) / (screenWidth + h) * (screenWidth * 0.5)) + "px";
                    top = ((screenHeight - h) / (screenHeight + h) * (screenHeight * 0.5)) + "px";
                    width = (h * Zooms[i].ratio) + "px";
                    height = h + "px";
                    zIndex = Math.round(10000 - h * 0.1);
                }
            }
            if (status == 1) {
                setTimeout("gallery.loop();", 16);
            }
        }
    },

    play_or_pause: function (target) {
        with (this) {
            if (status == 0) {
                status = 1;
                $(target).removeClass('off').addClass('on');
                loop();
            } else {
                status = 0;
                $(target).removeClass('on').addClass('off');
            }
        }
    },

    play: function () {
        with (this) {
            status = 1;
            screenWidth = parseInt($("#screen").css("width"));
            screenHeight = parseInt($("#screen").css("height"));
            images = $("#images img");
            for (var i = 0; i < images.length; i++) {
                Zooms[i] = new Zoom(i);
            }
            loop();
        }
    }
}


$(function () {
    $("#screen").css({
        width: $(window).width(),
        height: $(window).height()
    });

    $(document.body).bind('touchmove', function (e) {
        e.preventDefault();
    });

    $(document.body).doubletap(function (e) {
        e.preventDefault()
    });
});

window.onload = function () {
    gallery.play();
}