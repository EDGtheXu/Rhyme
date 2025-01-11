#version 330 core
in vec2 texCoord0;

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float Time;
uniform float Radius;
out vec4 fragColor;

float atan2(float y, float x) {
    float angle;
    float PI = 3.1415926;
    if (x > 0) {
        angle = atan(y / x);
    }
    else if (x < 0 && y >= 0) {
        angle = atan(y / x) + PI;
    }
    else if (x < 0 && y < 0) {
        angle = atan(y / x) - PI;
    }
    else if (x == 0 && y > 0) {
        angle = PI / 2.0;
    }
    else if (x == 0 && y < 0) {
        angle = -PI / 2.0;
    }
    else {
        angle = 0.0;
    }
    return angle;
}
void main(){
//    if(texture(Sampler0, texCoord0).a < 0.01) discard;

    vec2 uv = texCoord0 * 2 - 1; // 将 UV 坐标从 [0,1] 映射到 [-1,1]
    float r = max(max(abs(uv.x), abs(uv.y)), length(uv)*0.8)*Radius; // 计算半径
    float theta = atan(uv.y, uv.x) ; // 计算角度
    vec2 uvPolar = vec2(theta / (2 * 3.1415926), r);
    uvPolar.x = uvPolar.x * 4 + Time;
    uvPolar.y = r ;
    uvPolar.y = clamp(uvPolar.y, 0.0,1.0);
    vec4 col = texture(Sampler0, uvPolar);
    fragColor = col * ColorModulator;
}
