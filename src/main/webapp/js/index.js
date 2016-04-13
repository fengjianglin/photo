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

    LENGTH: 6, // 最大播放图片数量
    status: 0, // 0:暂停；1:播放
    target: null,
    photos: [],
    Zooms: [],
    screenWidth: 0,
    screenHeight: 0,
    start: 0,
    end: 0,
    callback: null,

    Zoom: function (i) {
        with (gallery) {
            this.span = document.createElement("span");
            this.span.setAttribute("class", 'span-slide');
            target.appendChild(this.span);

            var image = document.createElement("img");
            image.setAttribute("class", 'img-slide');
            image.setAttribute("src", photos[i].url);
            this.span.appendChild(image);

            this.N = i;
            this.ratio = photos[i].width / photos[i].height;

            if (i >= start && i < end) {
                $(this.span).show();
            } else {
                $(this.span).hide();
            }
        }
    },

    loop: function () {
        with (this) {
            for (var i = start; i < end; i++) {
                Zooms[i].N += 1 / 160;
                h = Math.pow(5, Zooms[i].N % (end - start));
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

    play_or_pause: function () {
        with (this) {
            if (status == 0) {
                status = 1;
                if (callback != null) {
                    callback("play");
                }
                loop();
            } else {
                status = 0;
                if (callback != null) {
                    callback("pause");
                }
            }
        }
    },

    animation: function () {
        alert("animation");
    },

    next: function () {
        with (this) {

            for (var i = start; i < end; i++) {
                $(Zooms[i].span).hide();
            }

            if (end < photos.length) {
                start = end;
                end += LENGTH;
                end = end > photos.length ? photos.length : end;
            } else {
                start = 0;
                end = photos.length > LENGTH ? LENGTH : photos.length;
            }

            for (var i = start; i < end; i++) {
                $(Zooms[i].span).show();
            }

        }
        console.log(this.start);
        console.log(this.end);
    },

    init: function (_target, _photos, _callback) {
        with (this) {
            callback = _callback;
            target = _target;
            photos = _photos;
            screenWidth = parseInt($(target).css("width"));
            screenHeight = parseInt($(target).css("height"));

            var m = photos.length % LENGTH;
            if (photos.length > 0 && m != 0) {
                for (var i = m; i < LENGTH; i++) {
                    photos[photos.length] = photos[i];
                }
            }

            start = 0;
            end = LENGTH;

            for (var i = 0; i < photos.length; i++) {
                Zooms[i] = new Zoom(i);
            }
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

