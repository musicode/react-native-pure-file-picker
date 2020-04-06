
#import "RNTFilePicker.h"

@interface RNTFilePicker()<UIDocumentPickerDelegate>

@end

@implementation RNTFilePicker

- (void)documentPicker:(UIDocumentPickerViewController *)controller didPickDocumentAtURL:(NSURL *)url {

    self.controller = nil;

    NSData *data = [NSData dataWithContentsOfURL:url];
    NSInteger size = [data length];

    NSString *path = url.absoluteString;
    NSString *prefix = @"file://";
    if ([path hasPrefix:prefix]) {
        path = [path substringFromIndex:[prefix length]];
    }

    self.resolve(@{
                   @"path": path,
                   @"name": path.lastPathComponent,
                   @"size": @(size)
                   });

}

- (void)documentPickerWasCancelled:(UIDocumentPickerViewController *)controller {
    self.controller = nil;
    self.reject(@"-1", @"cancel", nil);
}

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE(RNTFilePicker);

RCT_EXPORT_METHOD(open:(RCTPromiseResolveBlock) resolve reject:(RCTPromiseRejectBlock)reject) {

    self.resolve = resolve;
    self.reject = reject;

    // https://developer.apple.com/library/archive/documentation/Miscellaneous/Reference/UTIRef/Articles/System-DeclaredUniformTypeIdentifiers.html#//apple_ref/doc/uid/TP40009259
    NSArray *documentTypes = @[
                               @"com.adobe.pdf",
                               @"com.microsoft.word.doc",
                               @"com.microsoft.excel.xls",
                               @"com.microsoft.powerpoint.ppt",
                               @"com.microsoft.word.docx",
                               @"com.microsoft.excel.xlsx",
                               @"com.microsoft.powerpoint.pptx",
                               @"org.openxmlformats.wordprocessingml.document",
                               @"org.openxmlformats.spreadsheetml.sheet",
                               @"org.openxmlformats.presentationml.presentation"
                           ];

    self.controller = [[UIDocumentPickerViewController alloc] initWithDocumentTypes:documentTypes inMode:UIDocumentPickerModeImport];
    self.controller.delegate = self;

    UIViewController *rootViewController = [UIApplication sharedApplication].keyWindow.rootViewController;
    if (rootViewController != nil) {
        [rootViewController presentViewController:self.controller animated:YES completion:nil];
    }

}

@end
