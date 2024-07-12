struct Plane{
    double3 v0;
    double3 v1;
    double3 v2;
};

void debugVertex(double3 v){printf("[%d] %f %f %f \n", get_global_id(0), v.x, v.y, v.z);};
void debugPlane(struct Plane plane){
    printf("Plane: \n");
    debugVertex(plane.v0);
    debugVertex(plane.v1);
    debugVertex(plane.v2);
};


int rgbToInt(int r, int g, int b){
    return (r * 256*256) + (g * 256) + b;
};

double3 getNormal(struct Plane plane){
    return cross(plane.v0 - plane.v1, plane.v0 - plane.v2);
};
double getAreaOfPlane(struct Plane plane){
    return length(cross(plane.v0 - plane.v1, plane.v0 - plane.v2))/2.0;
};
double getAreaOfEdges(double3 edge0, double3 edge1){
    return length(cross(edge0, edge1))/2.0;
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

double4 planeRayIntersection(double3 direction, struct Plane plane){
    const double EPSILON = 0.00001;

    double3 v0 = plane.v0;
    double3 v1 = plane.v1;
    double3 v2 = plane.v2;


    

    double3 normal = getNormal(plane);
    if (length(normal) == 0){
        debugPlane(plane);
        return (double4)(0, 0, 0, -1);
    } 
    double parallelityWithNormal = 1.0f - length(cross(normal, direction));
    
    if (-EPSILON <= parallelityWithNormal && parallelityWithNormal <= EPSILON){
        return (double4)(0, 0, 0, -2);
    }

    double distanceToPlane = dot(normal, v0);

    double directionCoefficient = distanceToPlane / dot(normal, direction);
    // facing back
    if (directionCoefficient < 0) return (double4)(0, 0, 0, -3);

    double3 onPlanePosition = direction * directionCoefficient;
    double actualArea = getAreaOfPlane(plane);

    // points to poses
    v0 -= onPlanePosition;
    v1 -= onPlanePosition;
    v2 -= onPlanePosition;

    double area0 = getAreaOfEdges(v0, v1);

    // premature
    if (area0 > actualArea) return (double4)(0, 0, 0, -4);

    double area1 = getAreaOfEdges(v1, v2);
    double area2 = getAreaOfEdges(v2, v0);

    if (area0 + area1 + area2 > actualArea + EPSILON) return (double4)(0, 0, 0, -4);

    //return (double4)(0, 0, 1, 0);
    return (double4)(onPlanePosition, 1);
};

int rayTrace(double const *poses, double3 direction, int planesAmount){
    for (int i = 0; i < planesAmount; i++){
        struct Plane plane = getPlane(poses, i);
        double4 hit = planeRayIntersection(direction, plane);
        if (hit.w == 1) return 23445;
    }
    return 0;
};

__kernel void rayTracer
    (
    __global double *hits,
    __global int *pixelPlane,
    __global const double *poses,
    __global const int *planesAmount

    ) 
    {
    // TODO make params

    const int width = 700;
    const int height = 700;
    const int fov = 2;
    const float2 cameraRotation = (float2) (0, 0); 
    //

    const double fovMultiplier = width/2.0;
    const double xOffset = -width/2.0;
    const double yOffset = -height/2.0;

    const int id = get_global_id(0);
    
    //printf("%d ", poses[0]);

    //rotateVectorAlongCamera(cameraRotation, direction);
    int y = id / width;
    int x = id - y*width;

    //double3 v = (double3)(poses[0*3], poses[0*3+1], poses[0*3+2]);
    //debugVertex(v);

    //printf("id:%d x:%d y:%d \n", id, x, y);

    double3 direction = (double3) (x+xOffset, y+yOffset, fovMultiplier);

    //pixelPlane[id] = 2383783;
    pixelPlane[id] = rayTrace(poses, direction, planesAmount[0]);
}
