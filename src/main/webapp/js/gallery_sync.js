
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
                if(Zooms[i] == null){
                    continue;
                }
                Zooms[i].N += 1 / 160;
                var h = Math.pow(5, Zooms[i].N % (end - start));
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
                if(Zooms[i] == null){
                    continue;
                }
                Zooms[i].N += 1 / 160;
                var h = Math.pow(5, Zooms[i].N % LENGTH);
                var w = h * Zooms[i].ratio;

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

    Zoom: function (i, onload) {
        with (gallery) {
            this.span = document.createElement("span");
            this.span.setAttribute("class", 'span-slide');
            target.appendChild(this.span);

            var image = new Image();
            image.setAttribute("class", 'img-slide');
            image.span = this.span;
            image.onload = function() {
                this.span.appendChild(image);
                if(onload != null){
                    onload();
                }
            };
            image.src = photos[i].url;

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

            var init_zooms = function (_j){
                Zooms[_j] = new Zoom(_j, function(){
                    if(_j + 1 < photos.length){
                        init_zooms(_j + 1);
                    }
                });
            };
            init_zooms(0);

            currentAnimationIndex = Math.floor(Math.random() * animations.length);
        }
        return this;
    }
}