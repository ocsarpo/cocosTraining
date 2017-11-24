#include "HelloWorldScene.h"
#include "SimpleAudioEngine.h"

Scene* HelloWorld::createScene()
{
    return HelloWorld::create();
}

// Print useful error message instead of segfaulting when files are not there.
static void problemLoading(const char* filename)
{
    printf("Error while loading: %s\n", filename);
    printf("Depending on how you compiled you might have to add 'Resources/' in front of filenames in HelloWorldScene.cpp\n");
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !Scene::init() )
    {
        return false;
    }
    this->evenClick = false;
    auto visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.
    // add a "close" icon to exit the progress. it's an autorelease object
    auto closeItem = MenuItemImage::create(
                                           "CloseNormal.png",
                                           "CloseSelected.png",
                                           CC_CALLBACK_1(HelloWorld::menuCloseCallback, this));

    if (closeItem == nullptr ||
        closeItem->getContentSize().width <= 0 ||
        closeItem->getContentSize().height <= 0)
    {
        problemLoading("'CloseNormal.png' and 'CloseSelected.png'");
    }
    else
    {
        float x = origin.x + visibleSize.width - closeItem->getContentSize().width/2;
        float y = origin.y + closeItem->getContentSize().height/2;
        closeItem->setPosition(Vec2(x,y));
    }

    // create menu, it's an autorelease object
    auto menu = Menu::create(closeItem, NULL);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);

    /////////////////////////////
    // 3. add your codes below...

    // add a label shows "Hello World"
    // create and initialize a label
    auto label = Label::createWithTTF("Hello SMX-Tapes", "fonts/Marker Felt.ttf", 24);
    if (label == nullptr)
    {
        problemLoading("'fonts/Marker Felt.ttf'");
    }
    else
    {
        // position the label on the center of the screen
//        label->setPosition(Vec2(origin.x + visibleSize.width/2,
//                                origin.y + visibleSize.height - label->getContentSize().height));
        label->setPosition(Vec2(origin.x + visibleSize.width/4, origin.y+visibleSize.height/2));
        label->setRotation(-90);
        // add the label as a child to this layer
        this->addChild(label, 1);
    }

    // add "HelloWorld" splash screen"
    auto sprite = Sprite::create("iu.jpg");
    if (sprite == nullptr)
    {
        problemLoading("'iu.jpg'");
    }
    else
    {
        // position the sprite on the center of the screen
        sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));

        sprite->setRotation(-90);
        // add the sprite as a child to this layer
        this->addChild(sprite, 0);
    }
    auto spr2 = Sprite::create("k.jpg");
    spr2->setAnchorPoint(Point::ZERO);
    spr2->setScale(0.5);
    spr2->setTag(TAG_SPRITE_IMAGE);
    this->addChild(spr2);
//    auto label2 = Label::createWithTTF("K 케빵이 K", "fonts/Marker Felt.ttf", 12);
    auto label2 = Label::createWithSystemFont("K 케빵이 K", "Ariel", 12);
    label2->setPosition(Vec2(origin.x + visibleSize.width/7, origin.y+visibleSize.height/2));
    label2->setTag(TAG_K_TXT);
    this->addChild(label2,1);

    auto listener = EventListenerTouchOneByOne::create();
    listener->onTouchBegan  = CC_CALLBACK_2(HelloWorld::onTouchBegan,this);
    listener->onTouchMoved = CC_CALLBACK_2(HelloWorld::onTouchMoved, this);
    listener->onTouchEnded = CC_CALLBACK_2(HelloWorld::onTouchEnded, this);
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener,this);
    return true;
}

void HelloWorld::compareImage(Vec2 onTouchBeganLocation) {
    Vec2 location = onTouchBeganLocation;
    auto spr = (Sprite*)this->getChildByTag(TAG_SPRITE_IMAGE);
    auto label = (Label*)this->getChildByTag(TAG_K_TXT);

    CCLOG("spr x=%f, y=%f", spr->getPosition().x, spr->getPosition().y);
    CCLOG("spr x=%f, y=%f", spr->getContentSize().height, spr->getContentSize().width);

    if(location.x >= spr->getPosition().x){
        if(location.y >= spr->getPosition().y){
            if(location.x <= (spr->getPosition().x + spr->getContentSize().width)){
                if(location.y <= (spr->getPosition().y + spr->getContentSize().height)){
                    if(this->evenClick)
                        spr->setScale(2.0f);
                    else
                        spr->setScale(0.5f);
                    this->evenClick ^= true;

//                    JniMethodInfo t;
//                    if(JniHelper::getStaticMethodInfo(t, "org/cocos2dx/cpp/AppActivity","HelloJNI", "()Ljava/lang/String;")) {
//                        jstring result = (jstring) t.env->CallStaticObjectMethod(t.classID,
//                                                                                 t.methodID);
//                        const char *rev = t.env->GetStringUTFChars(result, 0);
//                        label->setString(rev);
//                        t.env->ReleaseStringUTFChars(result, rev);
//                        t.env->DeleteLocalRef(t.classID);
//                    }
                    /**
                     * java의 정적메서드를 호출하는 방법 (액티비티를 먼저 불러온 후, 메서드 호출)
                     */
//                    JniMethodInfo t;
//                    bool isHave = JniHelper::getStaticMethodInfo(t, "org/cocos2dx/cpp/AppActivity", "getThisActivity", "()Ljava/lang/Object;");
//                    jobject activityObj;
//                    if(isHave){
//                        activityObj=t.env->CallStaticObjectMethod(t.classID, t.methodID);
//                    }
//                    isHave = JniHelper::getMethodInfo(t, "org/cocos2dx/cpp/AppActivity","HelloJNI", "()Ljava/lang/String;");
//                    if(isHave){
//                        jstring result = (jstring) t.env->CallObjectMethod(activityObj, t.methodID);
//                        const char *rev = t.env->GetStringUTFChars(result, 0);
//                        label->setString(rev);
//                        t.env->ReleaseStringUTFChars(result, rev);
//                        t.env->DeleteLocalRef(t.classID);
//                    }
                    JniMethodInfo t;
                    bool isHave = JniHelper::getStaticMethodInfo(t, "org/cocos2dx/cpp/AppActivity", "getThisActivity", "()Ljava/lang/Object;");
                    jobject activityObj;
                    if(isHave){
                        activityObj=t.env->CallStaticObjectMethod(t.classID, t.methodID);
                    }
                    isHave = JniHelper::getMethodInfo(t, "org/cocos2dx/cpp/AppActivity","albumart", "()Ljava/lang/String;");
                    if(isHave){
                        jstring result = (jstring) t.env->CallObjectMethod(activityObj, t.methodID);
                        const char *rev = t.env->GetStringUTFChars(result, 0);
                        spr->setTexture (Director::getInstance()->getTextureCache()->addImage(rev));

                        t.env->ReleaseStringUTFChars(result, rev);
                        t.env->DeleteLocalRef(t.classID);
                    }
                }
            }
        }
    }
}

bool HelloWorld::onTouchBegan(Touch *touch, Event *unused_event) {
    Vec2 location = touch->getLocation();
    CCLOG("onTouchBegan X = %f, Y = %f", location.x, location.y);
    compareImage(location);
    return true;
}

void HelloWorld::onTouchMoved(Touch *touch, Event *unused_event) {
    CCLOG("onTouchMoved");
}
void HelloWorld::onTouchEnded(Touch *touch, Event *unused_event) {
    CCLOG("onTOuchEnded");
}

void HelloWorld::menuCloseCallback(Ref* pSender)
{
    //Close the cocos2d-x game scene and quit the application
    Director::getInstance()->end();

    #if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif

    /*To navigate back to native iOS screen(if present) without quitting the application  ,do not use Director::getInstance()->end() and exit(0) as given above,instead trigger a custom event created in RootViewController.mm as below*/

    //EventCustom customEndEvent("game_scene_close_event");
    //_eventDispatcher->dispatchEvent(&customEndEvent);


}
