<#include "../include/imports.ftl">


<div>

<#if pageable??>

    <div>

        <#list pageable.items as document>
            <article class="has-edit-button">
                <h3><a href="<@hst.link hippobean=document/>">${document.name}</a></h3>
                <p>
                    ${document.description}
                </p>
            </article>
        </#list>

    </div>

</#if>

</div>
