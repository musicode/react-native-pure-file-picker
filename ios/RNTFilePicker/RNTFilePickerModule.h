
#import <React/RCTViewManager.h>
#import <React/RCTBridgeModule.h>

@interface RNTFilePickerModule : NSObject <RCTBridgeModule>

@property (nonatomic, strong) UIDocumentPickerViewController *controller;
@property (nonatomic, strong) RCTPromiseResolveBlock resolve;
@property (nonatomic, strong) RCTPromiseRejectBlock reject;

@end
