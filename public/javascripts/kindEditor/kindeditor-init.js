KindEditor.ready(function(K) {
      window.editor= K.create('.editor', {
                width: "100%",
                height: '300px',
                allowFileManager: true,
                uploadJson: '/uploadFile',
                fileManagerJson: '/fileManager',
                themeType: 'simple',
                syncType: 'form'
        });
});