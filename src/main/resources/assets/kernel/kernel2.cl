struct Plane{
    double3 v0;
    double3 v1;
    double3 v2;
};
int rgbToInt(int r, int g, int b){
    return (r * 256*256) + (g * 256) + b;
}

double3 getNormal(struct Plane plane){
    cross(plane.v0 - plane.v1, plane.v2 - plane.v1);
};
double getAreaOfPlane(struct Plane plane){
    return length(cross(plane.v0 - plane.v1, plane.v0 - plane.v2))/2.0f;
};
double getAreaOfEdges(double3 edge0, double3 edge1){
    return length(cross(edge0, edge1))/2.0f;
};
double3 getVertex(double const *poses, int number){
    return (double3)(poses[number*3], poses[number*3+1], poses[number*3+2]);
};
struct Plane getPlane(double const *poses, int number){
    return (struct Plane){
        getVertex(poses, number),
        getVertex(poses, number+1),
        getVertex(poses, number+2)
    };
};
void rotateVectorAlongCamera(float2 cameraRotation, double3 vector){// TODO realise
};

void debugVertex(double3 v){printf("%d %d %d \n", v.x, v.y, v.z);}

double4 planeRayIntersection(double3 direction, struct Plane plane){
    const double EPSILON = 0.00001;

    double3 v0 = plane.v0;
    double3 v1 = plane.v1;
    double3 v2 = plane.v2;

    double3 normal = getNormal(plane);
    // printf("\n");
    // debugVertex(v0);
    // debugVertex(v1);
    // debugVertex(v2);
    // printf("\n");
    //printf("%d %d %d \n", normal.x, normal.y, normal.z);
    double parallelityWithNormal = 1.0f - length(cross(normal, direction)); 
    
    if (-EPSILON <= parallelityWithNormal && parallelityWithNormal <= EPSILON){
        return (double4)(0, 0, 0, 0);
    }

    double distanceToPlane = dot(normal, v0);
    double directionCoefficient = distanceToPlane / dot(normal, direction);
    // facing back
    if (directionCoefficient < 0) return (double4)(0, 0, 0, -1);

    double3 onPlanePosition = direction * directionCoefficient;
    double actualArea = getAreaOfPlane(plane);

    // points to poses
    v0 -= onPlanePosition;
    v1 -= onPlanePosition;
    v2 -= onPlanePosition;

    double area0 = getAreaOfEdges(v0, v1);

    // premature
    if (area0 > actualArea) return (double4)(0, 0, 0, -2);

    double area1 = getAreaOfEdges(v1, v2);
    double area2 = getAreaOfEdges(v2, v0);

    if (area0 + area1 + area2 > actualArea + EPSILON) return (double4)(0, 0, 0, -3);

    return (double4)(onPlanePosition, 1);
};
int rayTrace(double const *poses, double3 direction, int planesAmount){
    for (int i = 0; i < planesAmount; i++){
        struct Plane plane = getPlane(poses, i);
        double4 hit = planeRayIntersection(direction, plane);
        if (hit.w == 1) return rgbToInt(255, 255, 255);

        if (hit.w == 0) return rgbToInt(255, 0, 0);
        if (hit.w == -1) return rgbToInt(0, 255, 0);
        if (hit.w == -2) return rgbToInt(0, 0, 255);
        if (hit.w == -3) return rgbToInt(128, 128, 128);
    }
    return 0;
};

__kernel void rayTracer
    (
    __global const double *poses,
    __global const int *planesAmount,
    __global double *hits,
    __global int *pixelPlane
    ) 
    {
    // TODO make params
    printf("%d ", poses[0]);

    const int width = 700;
    const int height = 700;
    const int fov = 2;
    const float2 cameraRotation = (float2) (0, 0); 
    //

    const double fovMultiplier = width/2.0;
    const double xOffset = -width/2.0;
    const double yOffset = -height/2.0;

    const int id = get_global_id(0);
    

    //rotateVectorAlongCamera(cameraRotation, direction);
    int y = id / width;
    int x = id - y*width;

    //printf("id:%d x:%d y:%d \n", id, x, y);

    double3 direction = (double3) (x+xOffset, y+yOffset, fovMultiplier);


    //pixelPlane[id] = rayTrace(poses, direction, planesAmount[0]);
}
