/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package npanday.executable.execution.shells;

import npanday.executable.execution.ArgumentQuotingStrategy;
import org.codehaus.plexus.util.cli.shell.CmdShell;

import java.util.List;

/**
 * <i>Workaround for https://jira.codehaus.org/browse/PLXUTILS-147</i>
 *
 * @author <a href="mailto:lcorneliussen@apache.org">Lars Corneliussen</a>
 */
public class ExtendedCmdShell
    extends CmdShell
{
    private ArgumentQuotingStrategy quotingStrategy;

    public ExtendedCmdShell( ArgumentQuotingStrategy quotingStrategy )
    {
        this.quotingStrategy = quotingStrategy;

        // is turned off by default for CmdShell
        setDoubleQuotedArgumentEscaped( true );
    }

    @Override
    protected List getRawCommandLine( String executable, String[] arguments )
    {
        char[] executableEscapeChars = getEscapeChars(
            isSingleQuotedExecutableEscaped(), isDoubleQuotedExecutableEscaped()
        );

        char[] argumentEscapeChars = getEscapeChars(
            isSingleQuotedArgumentEscaped(), isDoubleQuotedArgumentEscaped()
        );

        return ShellUtils.buildCommandLine(
            quotingStrategy, executable, arguments,
            /* executable modification */
            getExecutionPreamble(), isQuotedExecutableEnabled(), executableEscapeChars, getExecutableQuoteDelimiter(),
            /* arguments modification */
            isQuotedArgumentsEnabled(), argumentEscapeChars, getArgumentQuoteDelimiter(), getQuotingTriggerChars()
        );
    }
}

