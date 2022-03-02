<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script>
    alert("${result.result}:${result.message}");
    location.href = "${destination}";
</script>