# 環境構築

## フロントエンド

## node.js

Node js インストール

```brew install node```

フロントエンドプロジェクトへフォルダ移動

```cd frontend```

ライブラリインストール


```yarn install```

起動

```yarn start```

## バックエンド

### Java8

インストール

```brew cask install homebrew/cask-versions/adoptopenjdk8```

~/.zprofileに下記の行を追加し、パスを設定する

```
export JAVA_HOME=`/usr/libexec/java_home -v "1.8"`
PATH=${JAVA_HOME}/bin:${PATH}
```

パスを更新する

```
source ~/.zprofile
```

参考記事：https://qiita.com/d_forest/items/290bb05bb929e5d74647

### scala

インストール

```brew install sbt```

バックエンドプロジェクトへフォルダ移動

```cd backend```

起動

```sbt run```

### データベース docker

Dockerインストール

```brew cask install docker```

Mysqlインストール

```brew install mysql```

起動

```docker-compose up```

プロジェクト配下で下記のコマンドを叩き、データベースを初期化する。

```sh initial-data.sh ```

パスワードの入力が求められたらrootと入力

```pasword:_ #root ```

