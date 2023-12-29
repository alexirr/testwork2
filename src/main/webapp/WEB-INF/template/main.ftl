<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <link rel="stylesheet" href="./style.css">
    <link rel="icon" href="./favicon.ico" type="image/x-icon">
</head>
<body>
<main>
    <#if username== 'Anon'>
        <a href="${context_path}/login">Войти</a>
        <a href="${context_path}/registration">Зарегистрироваться</a>
    <#else>
        <p>Здравствуйте ${username}</p>
        <a href="${context_path}/logout">Выйти</a>
    </#if>
</main>
<script src="index.js"></script>
</body>
</html>