#import <React/RCTBridgeModule.h>

@interface RNTFilePicker : NSObject <RCTBridgeModule>

@property (nonatomic, strong) UIDocumentPickerViewController *controller;
@property (nonatomic, strong) RCTPromiseResolveBlock resolve;
@property (nonatomic, strong) RCTPromiseRejectBlock reject;

@end
