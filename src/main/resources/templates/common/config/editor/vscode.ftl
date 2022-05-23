<#--
 ~ Copyright (c) 2022 Mayeku Khisa
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 -->
<#macro settings>
   "editor.formatOnSave": true,
   "editor.rulers": [
      100
   ],
   "editor.wordWrapColumn": 100,
   "editor.wordWrap": "wordWrapColumn",
   "editor.wrappingIndent": "indent",
   "[jsonc]": {
      "editor.defaultFormatter": "vscode.json-language-features"
   }
</#macro>
{
<@settings/>
}
