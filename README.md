# DoogyParadise寵物平台網站

這是一個關於寵物的多系統平台，包含會員、商城、活動、旅館、動態牆。
會員系統:登入、註冊、忘記密碼、Google OAuth2第三方登入。

## 這是網站的後端程式
使用Maven專案Spring Bootd框架進行開發，所有的依賴都在pom檔，properties設定請看以下的快速開始。

## 環境
- MySQL:8.0.31
- JDK 17
## 功能特色

- 功能1：串接第3方
    - Google OAuth2第三方登入
    - Line Pay、綠界支付
    - Google Gemini API
     
- 功能2：開發風格
    - RESTful
    - 前後端分離
    - 後端三層式架構:dao、service、controller

## 快速開始

- 使用Spring Boot在Maven專案運行localhost 8080，Vue專案使用vite套件運行在localhost 5173。

- SQL Query位於DoogyParadise_SQL.sql，可直接於MySQL建置資料庫環境，員工須於資料庫自行新增一筆。

- application.properties與Google第三方登入的properties設定為application_properties_config.txt及google_OAuth2_config.txt，需自行設定(並放入專案resource資料夾中)。

- Google OAuth2第三方登入需要申請Google Cloud 第三方登入申請憑證(如果不想使用可以註解掉)。

- Google Gemini API:需自行去Google AI Studio申請API Key，截至2024/4月底前都是免費。

- 網站內mail發信功能必須至google帳號開通二階段驗證與應用程式密碼。
