
int intToColor(int r, int g, int b){
    return ((r & 0xff) << 16) + ((g & 0xff) << 8) + (b & 0xff);
};

int floatToColor(float r, float g, float b){
    return intToColor(((int) r*255), ((int) g*255), ((int) b*255));
};

__kernel void rayCast(__global int *c) {
    int gid = get_global_id(0);
    float3 vec = (float3)(1.0, 1.0, 1.0);
    c[gid] = floatToColor(vec.x, vec.y, vec.z);
};