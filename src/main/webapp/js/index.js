var music = {
    status: 0, // 0:暂停；1:播放
    list: ["/raw/andxj.m4a",
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
        "/raw/zyxc.m4a"],

    currentMusicIndex: 0,

    randomMusicUrl: function () {
        var i = Math.floor(Math.random() * music.list.length);
        if (this.currentMusicIndex == i) {
            return this.randomMusicUrl();
        }
        return music.list[i];
    },

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
                $(audio).attr("src", this.randomMusicUrl());
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
        $(audio).attr("src", this.randomMusicUrl());
        audio.load();
        audio.play();
    }
}

var gallery = {

    LENGTH: 6, // 最大播放图片数量
    status: 0, // 0:暂停；1:播放
    target: null,
    photos: [],
    Zooms: [],
    WIDTH: 0,
    HEIGHT: 0,
    start: 0,
    end: 0,
    callback: null,
    currentAnimationIndex: 0,
    animations: [function () {
        with (gallery) {
            for (var i = start; i < end; i++) {
                Zooms[i].N += 1 / 160;
                h = Math.pow(5, Zooms[i].N % (end - start));
                with (Zooms[i].span.style) {
                    left = ((WIDTH - (h * Zooms[i].ratio)) / (WIDTH + h) * (WIDTH * 0.5)) + "px";
                    top = ((HEIGHT - h) / (HEIGHT + h) * (HEIGHT * 0.5)) + "px";
                    width = (h * Zooms[i].ratio) + "px";
                    height = h + "px";
                    zIndex = Math.round(10000 - h * 0.1);
                }
            }
        }
    }, function () {
        with (gallery) {
            for (var i = start; i < end; i++) {
                Zooms[i].N += 1 / 160;
                h = Math.pow(5, Zooms[i].N % LENGTH);
                w = h * Zooms[i].ratio;

                with (Zooms[i].span.style) {
                    width = w + "px";
                    height = h + "px";
                    if (w > WIDTH) {
                        left = (WIDTH - w) / 2 + "px";
                    } else {
                        left = 0 + "px";
                    }
                    if (h > HEIGHT) {
                        top = (HEIGHT - h) / 2 + "px"
                    } else {
                        top = (HEIGHT - h) + "px";
                    }
                    zIndex = Math.round(10000 - h * 0.1);
                }
            }
        }
    }],

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
            animations[currentAnimationIndex]();
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
        with (this) {
            var i = Math.floor(Math.random() * animations.length);
            if (currentAnimationIndex == i) {
                animation();
            } else {
                currentAnimationIndex = i;
            }
        }
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
    },

    init: function (_target, _photos, _callback) {
        with (this) {
            callback = _callback;
            target = _target;
            photos = _photos;
            WIDTH = parseInt($(target).css("width"));
            HEIGHT = parseInt($(target).css("height"));

            var m = photos.length % LENGTH;
            if (photos.length > 0 && m != 0) {
                for (var i = m; i < LENGTH; i++) {
                    photos[photos.length] = photos[i - m];
                }
            }

            start = 0;
            end = LENGTH;

            for (var i = 0; i < photos.length; i++) {
                Zooms[i] = new Zoom(i);
            }

            currentAnimationIndex = Math.floor(Math.random() * animations.length);
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

