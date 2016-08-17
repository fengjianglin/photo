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
    audio: null,
    callback: null,
    randomMusicUrl: function () {
        var i = Math.floor(Math.random() * music.list.length);
        if (this.currentMusicIndex == i) {
            return this.randomMusicUrl();
        }
        this.currentMusicIndex = i;
        return music.list[i];
    },
    init: function(_audio, _callback){
        with (this) {
            audio = _audio;
            callback = _callback;
        }
        return this;
    },
    play: function(){
        if (this.status == 0) {
            this.status = 1;
            if (!!!$(this.audio).attr("src")) {
                $(this.audio).attr("src", this.randomMusicUrl());
                this.audio.load();
            }
            this.audio.play();
            if (this.callback != null) {
                this.callback("play");
            }
        }
    },
    pause: function(){
        if (this.status == 1) {
            this.status = 0;
            this.audio.pause();
            if (this.callback != null) {
                this.callback("pause");
            }
        }
    },
    play_or_pause: function () {
        if (this.status == 1) {
            this.pause();
        } else {
            this.play();
        }
    },

    random: function () {
        if (this.status == 0) {
            this.status = 1;
            if (this.callback != null) {
                this.callback("play");
            }
        }
        $(this.audio).attr("src", this.randomMusicUrl());
        this.audio.load();
        this.audio.play();
    }
}

function countdown(time) {

    if (time <= 0) {
        window.location.reload();
    }
    var diff = time;
    var hours = Math.floor(diff / (1000 * 60 * 60));
    diff -= hours * (1000 * 60 * 60);

    var minutes = Math.floor(diff / (1000 * 60));
    diff -= minutes * (1000 * 60);

    var seconds = Math.floor(diff / (1000));
    diff -= seconds * (1000);

    if (hours < 10)
        hours = '0' + hours;

    if (minutes < 10)
        minutes = '0' + minutes;

    if (seconds < 10)
        seconds = '0' + seconds;

    var text = hours + ":" + minutes + ":" + seconds;
    $("#countdown").text(text);

    setTimeout("countdown(" + (time - 1000) + ");", 1000);
}

