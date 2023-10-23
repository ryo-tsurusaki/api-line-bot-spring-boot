# api

### package構成

| package        | ディレクトリ      | 説明                                     |
|----------------|-------------|----------------------------------------|
| application    | service     | ApplicationService                     |
|                | shared      | サービス間で使いたいサービス                         |
| domain         | model       | 値オブジェクトなどを格納したオブジェクト                   |
|                | repository  | インターフェース                               |
|                | service     | DomainService                          |
|                | value       | 値オブジェクトや区分オブジェクト                       |
| infrastructure | composite   | 複数のdatasourceやexternalの結果を結合したりする場合に使用 |
|                | datasource  | DBなど                                   |
|                | external    | 外部APIなど                                |
|                | transfer    | 外部ストレージなど                              |
| presentation   | controller  | Controller                             |
|                | security    | 認証関連やCSRFトークンなどの処理                     |
|                | interceptor | コントローラー共通処理                            |

### 以下は未整理

AWS App Runnerのチュートリアル
https://aws.amazon.com/jp/builders-flash/202303/spring-boot-app-with-apprunner-cdk/?awsf.filter-name=*all


コマンド

```
// 実行
$ ./gradlew bootRun

// ビルド
$ ./gradlew build

// Jar実行
$ java -jar {jarファイル}

// Image作成
$ docker build -t tsurusaki/api-spring-boot .

// Image実行
$ docker run -p 8080:8080 tsurusaki/api-spring-boot
```

App Runner デプロイ時に参考にした<br>
https://aws.amazon.com/jp/builders-flash/202303/spring-boot-app-with-apprunner-cdk/?awsf.filter-name=*all
