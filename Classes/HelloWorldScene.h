#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__
#include "cocos2d.h"
#include "platform/android/jni/JniHelper.h"
#include "jni.h"

USING_NS_CC;

#define TAG_SPRITE_IMAGE 1000
#define TAG_K_TXT 1001

class HelloWorld : public cocos2d::Scene
{
public:
    bool evenClick;
public:
    static cocos2d::Scene* createScene();
    virtual bool init();
    
    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);
    
    // implement the "static create()" method manually
    CREATE_FUNC(HelloWorld);
    void compareImage(Vec2 onTouchBeganLocation);
    void changeAlbum(const char* path);

    bool onTouchBegan(Touch *touch, Event *unused_event);
    void onTouchMoved(Touch *touch, Event *unused_event);
    void onTouchEnded(Touch *touch, Event *unused_event);


};

#endif // __HELLOWORLD_SCENE_H__
