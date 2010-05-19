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
package npanday.plugin.compile;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import npanday.InitializationException;
import npanday.assembler.AssemblerContext;
import npanday.artifact.AssemblyResolver;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This class initializes and validates the setup.
 *
 * @author Shane Isbell
 * @goal initialize
 * @phase compile
 * @description Initializes and validates the setup.
 */
public class ComponentInitializerMojo
    extends AbstractMojo
{

    /**
     * The maven project.
     *
     * @parameter expression="${project}"
     * @required
     */
    private MavenProject project;

    /**
     * @parameter expression="${settings.localRepository}"
     * @readonly
     */
    private File localRepository;

    /**
     * @component
     */
    private AssemblyResolver assemblyResolver;

    /**
     * @component
     */
    private AssemblerContext assemblerContext;

    public void execute()
        throws MojoExecutionException
    {
        long startTime = System.currentTimeMillis();

        if ( localRepository == null )
        {
            localRepository = new File( System.getProperty( "user.home" ), ".m2/repository" );
        }

        try
        {
            assemblyResolver.resolveTransitivelyFor( project, project.getDependencies(),
                                                     project.getRemoteArtifactRepositories(), localRepository, true );

            // we don't actually want transitive dependencies, so take the resolved set and cull back to the same as the
            // project specifies. TODO: we should be able to simplify this mojo to use Maven's dependency resolution mechanism if we have a way to resolve the GAC/COM dependencies
            for ( Iterator i = project.getDependencyArtifacts().iterator(); i.hasNext(); )
            {
                Artifact artifact = (Artifact) i.next();

                boolean found = false;
                for ( Iterator j = project.getDependencies().iterator(); j.hasNext() && !found; )
                {
                    Dependency dependency  = (Dependency) j.next();
                    if ( dependency.getGroupId().equals( artifact.getGroupId() ) && dependency.getArtifactId().equals(
                        artifact.getArtifactId() ) )
                    {
                        found = true;
                    }
                }

                if ( !found )
                {
                    i.remove();
                }
            }
                                                     
             // we don't actually want transitive dependencies, so take the resolved set and cull back to the same as the
            // project specifies. TODO: we should be able to simplify this mojo to use Maven's dependency resolution mechanism if we have a way to resolve the GAC/COM dependencies
            for ( Iterator i = project.getDependencyArtifacts().iterator(); i.hasNext(); )
            {
                Artifact artifact = (Artifact) i.next();
                System.out.println(">>>>>. artifact " + artifact.getId());
                boolean found = false;
                for ( Iterator j = project.getDependencies().iterator(); j.hasNext() && !found; )
                {
                    Dependency dependency  = (Dependency) j.next();
                    if ( dependency.getGroupId().equals( artifact.getGroupId() ) && dependency.getArtifactId().equals(
                        artifact.getArtifactId() ) && dependency.getVersion().equals(artifact.getVersion()) )
                    {
                        found = true;
                    }
                }
                

                if ( !found )
                {
                    i.remove();
                }
            }

        }
        catch ( java.io.IOException e )
        {
            throw new MojoExecutionException(e.getMessage());
        }

        try
        {
            assemblerContext.init( project );
        }
        catch ( InitializationException e )
        {
            throw new MojoExecutionException( "NPANDAY-901-002: Failed to initialize the assembler context" );
        }

        long endTime = System.currentTimeMillis();
        getLog().info( "Mojo Execution Time = " + ( endTime - startTime ) );
    }
}
