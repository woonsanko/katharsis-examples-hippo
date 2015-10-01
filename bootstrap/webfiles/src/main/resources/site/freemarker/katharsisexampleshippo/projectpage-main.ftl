<#include "../include/imports.ftl">

<#-- @ftlvariable name="document" type="com.github.woonsanko.katharsis.examples.hippo.beans.Project" -->

<#if document??>

    <@hst.link var="link" hippobean=document/>

    <article class="has-edit-button">
        <@hst.cmseditlink hippobean=document/>
        <h3><a href="${link}">${document.name?html}</a></h3>
        <#if document.description??>
            <p>${document.description}</p>
        </#if>

        <h5>Tasks</h5>

        <ul>
            <#list document.tasks as task>
                <li>
                    <strong>${task.name}</strong>:
                    ${task.description}
                </li>
            </#list>
        </ul>

    </article>

</#if>
