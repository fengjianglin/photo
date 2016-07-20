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
        this.currentMusicIndex = i;
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

