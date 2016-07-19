/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Vars

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/


var ctx = null,
    particles = [],
    particleCount = 1000,
    particlePath = 4,
    pillars = [],
    pillarCount = 110,
    hue = 0,
    hueRange = 60,
    hueChange = 1,
    gravity = 0.1,
    PI = Math.PI,
    TWO_PI = PI * 2;


var storm = {
    WIDTH: 0,
    HEIGHT: 0,
    init: function (_canvas) {
        this.WIDTH = _canvas.width = parseInt($(_canvas).css("width"));
        this.HEIGHT = _canvas.height = parseInt($(_canvas).css("height"));
        ctx = _canvas.getContext('2d');
        ctx.lineWidth = 1;
        ctx.lineCap = "round";

        var i = pillarCount;
        while (i--) {
            pillars.push(new Pillar());
        }

        loop();
    }
}

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Utility

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function rand(min, max) {
    return Math.random() * ( max - min ) + min;
}

function distance(a, b) {
    var dx = a.x - b.x,
        dy = a.y - b.y;
    return Math.sqrt(dx * dx + dy * dy);
}

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Particle

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function Particle(opt) {
    this.path = [];
    this.reset();
}

Particle.prototype.reset = function () {
    this.radius = 1;
    this.x = rand(0, storm.WIDTH);
    this.y = 0;
    this.vx = 0;
    this.vy = 0;
    this.hit = 0;
    this.path.length = 0;
};

Particle.prototype.step = function () {
    this.hit = 0;

    this.path.unshift([this.x, this.y]);
    if (this.path.length > particlePath) {
        this.path.pop();
    }

    this.vy += gravity;

    this.x += this.vx;
    this.y += this.vy;

    if (this.y > storm.HEIGHT + 10) {
        this.reset();
    }

    var i = pillarCount;
    while (i--) {
        var pillar = pillars[i];
        if (distance(this, pillar) < this.radius + pillar.renderRadius) {
            this.vx = 0;
            this.vy = 0;
            this.vx += -( pillar.x - this.x ) * rand(0.01, 0.03);
            this.vy += -( pillar.y - this.y ) * rand(0.01, 0.03);
            pillar.radius -= 0.1;
            this.hit = 1;
        }
    }
};

Particle.prototype.draw = function () {
    ctx.beginPath();
    ctx.moveTo(this.x, ~~this.y);
    for (var i = 0, length = this.path.length; i < length; i++) {
        var point = this.path[i];
        ctx.lineTo(point[0], ~~point[1]);
    }
    ctx.strokeStyle = 'hsla(' + rand(hue + ( this.x / 3 ), hue + ( this.x / 3 ) + hueRange) + ', 50%, 30%, 0.3)';
    ctx.stroke();

    if (this.hit) {
        ctx.beginPath();
        ctx.arc(this.x, this.y, rand(1, 25), 0, TWO_PI);
        ctx.fillStyle = 'hsla(' + rand(hue + ( this.x / 3 ), hue + ( this.x / 3 ) + hueRange) + ', 80%, 15%, 0.1)'
        ctx.fill();
    }
};

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Pillar

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function Pillar() {
    this.reset();
}

Pillar.prototype.reset = function () {
    this.radius = rand(50, 100);
    this.renderRadius = 0;
    this.x = rand(0, storm.WIDTH);
    this.y = rand(storm.HEIGHT / 2 - storm.HEIGHT / 4, storm.HEIGHT);
    this.active = 0;
};

Pillar.prototype.step = function () {
    if (this.active) {
        if (this.radius <= 1) {
            this.reset();
        } else {
            this.renderRadius = this.radius;
        }
    } else {
        if (this.renderRadius < this.radius) {
            this.renderRadius += 0.5;
        } else {
            this.active = 1;
        }
    }
};

Pillar.prototype.draw = function () {
    ctx.beginPath();
    ctx.arc(this.x, this.y, this.renderRadius, 0, TWO_PI, false);
    ctx.fill();
};

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Step

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function step() {
    hue += hueChange;

    if (particles.length < particleCount) {
        particles.push(new Particle());
    }

    var i = particles.length;
    while (i--) {
        particles[i].step();
    }

    i = pillarCount;
    while (i--) {
        pillars[i].step();
    }
}

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Draw

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function draw() {
    ctx.fillStyle = 'hsla(0, 0%, 0%, 0.1)';
    ctx.fillRect(0, 0, storm.WIDTH, storm.HEIGHT);

    ctx.globalCompositeOperation = 'lighter';
    var i = particles.length;
    while (i--) {
        particles[i].draw();
    }

    ctx.globalCompositeOperation = 'source-over';
    i = pillarCount;
    ctx.fillStyle = 'rgba(20, 20, 20, 0.3)';
    while (i--) {
        pillars[i].draw();
    }
}

/*/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/

 Loop

 =/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=/=*/

function loop() {
    requestAnimationFrame(loop);
    step();
    draw();
}