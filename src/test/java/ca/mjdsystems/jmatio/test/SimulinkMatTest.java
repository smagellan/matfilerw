/*
 * Copyright 2014 Matthew Dawson <matthew@mjdsystems.ca>
 */
package ca.mjdsystems.jmatio.test;

import ca.mjdsystems.jmatio.io.MatFileFilter;
import ca.mjdsystems.jmatio.io.MatFileReader;
import ca.mjdsystems.jmatio.io.MatFileType;
import ca.mjdsystems.jmatio.io.SimulinkDecoder;
import ca.mjdsystems.jmatio.types.MLObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/** This test verifies that ReducedHeader generated mat files work correctly.
 *
 * @author Matthew Dawson <matthew@mjdsystems.ca>
 */
@RunWith(JUnit4.class)
public class SimulinkMatTest
{
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testParsingQuadraticRootsTET() throws IOException
    {
        File file = fileFromStream("/simulink_tet_out.mat");
        MatFileReader reader = new MatFileReader(file, new MatFileFilter(), MatFileType.ReducedHeader);
        MLObject data = (MLObject)reader.getContent().get("@");

        // First check that the root element is correct.
        assertThat(data, is(notNullValue()));
        assertThat(data.getClassName(), is("Data"));
    }

    // This just ensures the SimulinkDecoder actually decodes correctly.
    @Test
    public void testParsingSimulinkEncoding() throws IOException
    {
        String input = "  %)30     .    >     8    (    $0         !          $ ! !-0T]3 0 $ $1A=&$.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    !    !0    X    @3@  !@    @    )          4    (     0   /!-   !          (   #P30    %)30     .    4$T   8    (     @         %    \"     $    !     0         %  0 !0    $    %    34-/4P     .    \"$T   8    (    $0         !          $ ! !-0T]3 0    T   !&:6QE5W)A<'!E<E]?    #@   ,A,   &    \"     $         !0    @   \"B     0    $         #@   ' -   &    \"     D         !0    @   ! #0   0    $          @   $ -   \"    +    -@!   X @  . (  ) $  !X#   0 T             1W)I9#  0V5L;', 0V5L;#$ 8V]N9 !C;VYD7W1E>'0 8V5L;%]I;F1E> !P87)E;G1?9W)I9 !C96QL<P!'<FED '!A<F5N=%]C96QL '-P;&ET7W!B &YU;5]C96QL<P!G<FED7VEN9&5X ')'<FED &YE=U]C96QL7W!B &1E;&5T95]C96QL7W!B '!B7V9L86< 0V5L; !S=6)G<FED '=I9'1H &AE:6=H= !G<FED7W!B &-O;&]R &-O;F1I=&EO;E]T97AT7W=I9'1H &-O;F1I=&EO;E]T97AT7VAE:6=H= !C;VYD:71I;VY?=&5X=%]X &-O;F1I=&EO;E]T97AT7WD 8V]N9&ET:6]N7W1E>'1?;V9F<V5T &=R:61?<'5S:%]W:61T: !21W)I9 !'<FED,0!'<FED,@!#96QL,@!R97-U;'0 <F5S=6QT7W1E>'0 4D-E;&P 9G5N8W1I;VY?;F%M90!F=6YC=&EO;E]I;G!U=', <V5T=&EN9W, 8VAE8VME9 !O<&5N &9I9P!M=6QT:5]M;V1E $1A=&$                                    )                    $@                   !X                    D                    +                                               !0                    $    8     P                    (    7    !                     ,    /     @                    0    #     0                    4    \"     @                    8    !     @                    <    .     0                    @    -     @                    D    ,     0                    H    +     @                    L    *     0                    P    )     @                    T    $     @                    X    (     0                    \\    '     @                   !     %     @                   !$    &    !                    !(    0    !                    !,    1    !                    !0    2    !                    !4    3    !                    !8    4    !                    !<    5    !                    !@    6               *     0    $   \"5    'P    $   \"6    (     $   \"7    )0    $   \"8    )@    $   \"9    )P    $   \":    *     $   \";    *0    $   \"<    *@    $   \"=    *P    $   \">          ,    \"     0   )(    ?     0   ),    @     0   )0    %     P    $    0    (0    $   !K    (@    $   !L    (P    $   !M    %P    $   !N    !0    0    !          4    !     0    8    !     @    <    !    #@   !$    !    #P    8    (     0    @    ,     0    D    -     0    H    .     0    L    /     0    P    0     0    T         !0    0    !     P    4    !    !     8    !    !0    <    !    !@   !$    !    !P    D    $     0   !$    %     0   !(    &     0   !,    '     0   &4    4     0   &8    5     0   &<    6     0   &@    1     0   &D    7     0   &H    '    \"@    $   !>    \"     $   !?    #     $   !@    #0    $   !A    #@    $   !B    #P    $   !C    $     $   !D    \"0   !,    !    %     0    !    %0    4    !    %@    8    !    %P    <    !    60   !0    !    6@   !4    !    6P   !8    !    7    !<    !    70    8    (     0   %,    ,     0   %0    -     0   %4    .     0   %8    /     0   %<    0     0   %@         \"0   !,    !    2@    0    !    2P    4    !    3     8    !    30    <    !    3@   !0    !    3P   !4    !    4    !8    !    40   !<    !    4@    <    *     0   !@    (     0   $0    ,     0   $4    -     0   $8    .     0   $<    /     0   $@    0     0   $D    )    !     $    9    !0    $    :    !@    $    ;    !P    $    <    %     $    =    %0    $    >    %@    $    ?    $0    $    @    %P    $    A    \"0   !,    !    .P    0    !    /     4    !    /0    8    !    /@    <    !    /P   !0    !    0    !4    !    00   !8    !    0@   !<    !    0P    <    *     0   \"(    (     0   #4    ,     0   #8    -     0   #<    .     0   #@    /     0   #D    0     0   #H    )    !     $    C    !0    $    D    !@    $    E    !P    $    F    %     $    G    %0    $    H    %@    $    I    $0    $    J    %P    $    K    \"0    0    !    +     4    !    +0    8    !    +@    <    !    +P   !0    !    ,    !4    !    ,0   !8    !    ,@   !$    !    ,P   !<    !    -     4    #     0   &\\    A     0   '     B     0   '$    C     0   '(    7     0   ',    %     P    $   !T    (0    $   !U    (@    $   !V    (P    $   !W    %P    $   !X    !0    ,    !    >0   \"$    !    >@   \"(    !    >P   \",    !    ?    !<    !    ?0    4    #     0   'X    A     0   '\\    B     0   (     C     0   ($    7     0   ((    %     P    $   \"#    (0    $   \"$    (@    $   \"%    (P    $   \"&    %P    $   \"'    !0    ,    !    B    \"$    !    B0   \"(    !    B@   \",    !    BP   !<    !    C     4    #     0   (T    A     0   (X    B     0   (\\    C     0   )     7     0   )$                                                                                                                                                                                                                                                                               X         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 &:@0 X    P    !@    @    $          4    (     0    (    !         !   @!X,0  #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX    X    !@    @    &          4    (     0    $    !          D    (        $ !HH$ .    8     8    (     0         %    \"     $    !     0         .    ,     8    (    !          %    \"     $    \"     0         0  ( >#(   X    X    !@    @    &          4    (     0    $    !          D    (             $ .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    %     0    X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    4     8    (    #0         %    \"     <    !     0         &    '        -T\"     0    (    $    !@    (         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    \"     P    X    X    !@    @    &          4    (     0    $    !          D    (        $ !JH$ .    .     8    (    !@         %    \"     $    !     0         )    \"        !  ;*! #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    !0    $    .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    !     (    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  0J! #@   &@    &    \"     $         !0    @    !     0    $         #@   #@    &    \"     0         !0    @    !    !@    $         $     8   !B('X](#    X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    (     0    X    X    !@    @    &          4    (     0    $    !          D    (        $ ! H$ .    .     8    (    !          %    \"     $    &     0         0    !@   &$@/3T@,   #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    L    \"    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 %\"@0 X   !X    !@    @    !          4    (     0    $    !          X   !(    !@    @    $          4    (     0   !(    !         !     2    *&)>,BD@+2 T*F$J8R ]/2 P        #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    P    !    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    .     8    (    !@         %    \"     $    !     0         )    \"        !  4J! #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX   !(    !@    @    &          4    (     0    ,    !          D    8    4DH3'/*P[3\\P$LPJSHSN/]8\\F8DPW>\\_#@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    #@    (    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  6J! #@   &     &    \"     $         !0    @    !     0    $         #@   #     &    \"     0         !0    @    !    !     $         $  $ &(^/3 .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    #P    $    .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX    X    !@    @    &          4    (     0    $    !          D    (        $ !<H$ .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   $@    &    \"     8         !0    @    !     P    $         \"0   !@   \"^2I:7/./O/]6YL*CJ3>T_SA#^V^CG[S\\.    .     8    (    !@         %    \"     $    !     0         )    \"        !  7J! #@   &     &    \"     $         !0    @    !     0    $         #@   #     &    \"     0         !0    @    !     P    $         $  # &(\\,  .    .     8    (    !@         %    \"     $    !     0         )    \"             ! #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    #P    $    .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX    X    !@    @    &          4    (     0    $    !          D    (        $ !@H$ .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   $@    &    \"     8         !0    @    !     P    $         \"0   !@   \"^2I:7/./O/]6YL*CJ3>T_SA#^V^CG[S\\.    4     8    (    #0         %    \"     <    !     0         &    '        -T\"     0    (    0    $0    (         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X    X    !@    @    &          4    (     0    $    !          D    (             $ .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    \"     P    X    X    !@    @    &          4    (     0    $    !          D    (        $ !BH$ .    .     8    (    !@         %    \"     $    !     0         )    \"        !  9*! #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    #P    $    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  5*! #@   '@    &    \"     $         !0    @    !     0    $         #@   $@    &    \"     0         !0    @    !    $0    $         $    !$    H8EXR*2 M(#0J82IC(#X@,          .    .     8    (    !@         %    \"     $    !     0         )    \"             ! #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    #     $    .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @            $0 X    X    !@    @    &          4    (     0    $    !          D    (        L (FH$ .    2     8    (    !@         %    \"     $    #     0         )    &    %)*$QSRL.T_,!+,*LZ,[C_6/)F),-WO/PX   !0    !@    @    -          4    (    !P    $    !          8    <        W0(    !     @    T    .     @         .    .     8    (    !@         %    \"     $    !     0         )    \"             ! #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    (    #    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 %:@0 X    X    !@    @    &          4    (     0    $    !          D    (        $ !8H$ .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    ,     0    X    X    !@    @    &          4    (     0    $    !          D    (        $ !*H$ .    :     8    (     0         %    \"     $    !     0         .    .     8    (    !          %    \"     $    &     0         0    !@   &$@?CT@,   #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    H    !    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX    X    !@    @    &          4    (     0    $    !          D    (            $$ .    .     8    (    !@         %    \"     $    !     0         )    \"        & !')] #@   $@    &    \"     8         !0    @    !     P    $         \"0   !@    ,>:>].&CO/PU.(B/3LN\\_?6F6W=,T[3\\.    4     8    (    #0         %    \"     <    !     0         &    '        -T\"     0    (    )    \"P    (         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @             0 X    X    !@    @    &          4    (     0    $    !          D    (             $ .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    \"     P    X    X    !@    @    &          4    (     0    $    !          D    (        $ !,H$ .    .     8    (    !@         %    \"     $    !     0         )    \"        !  3J! #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    \"@    $    .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #X/PX    X    !@    @    &          4    (     0    $    !          D    (         $2 2$ .    2     8    (    !@         %    \"     $    #     0         )    &     QYI[TX:.\\_#4XB(].R[S]]:9;=TS3M/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    D    \"    #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    !P    (    .    .     8    (    !@         %    \"     $    !     0         )    \"             ! #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    (    #    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 $:@0 X    X    !@    @    &          4    (     0    $    !          D    (        $ !(H$ .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    (     0    X    X    !@    @    &          4    (     0    $    !          D    (             $ .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 $2@0 X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    2     8    (    !@         %    \"     $    #     0         )    &    . [L)D)N>\\_G<%2)=32[C]#/&N!M!SM/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    <    \"    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 &Z@0 X   !@    !@    @    !          4    (     0    $    !          X    P    !@    @    $          4    (     0    0    !         !  !  M8R]B#@   $@    &    \"     8         !0    @    !     P    $         \"0   !@           #P/P       / _        \\#\\.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    $     @    X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    T    \"    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 '\"@0 X   !H    !@    @    !          4    (     0    $    !          X    X    !@    @    $          4    (     0    8    !         !     &    +6(O,BIA   .    2     8    (    !@         %    \"     $    #     0         )    &            / _        \\#\\       #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    0    \"    #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    $     (    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  <J! #@   (     &    \"     $         !0    @    !     0    $         #@   %     &    \"     0         !0    @    !    '@    $         $    !X    H+6(@+2!S<7)T*&)>,B M(#0J82IC*2D@+R R*F$   X   !(    !@    @    &          4    (     0    ,    !          D    8            \\#\\       #P/P       / _#@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    !     (    .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    1     @    X    X    !@    @    &          4    (     0    $    !          D    (        $ !TH$ .    @     8    (     0         %    \"     $    !     0         .    4     8    (    !          %    \"     $    >     0         0    '@   \"@M8B K('-Q<G0H8EXR(\"T@-\"IA*F,I*2 O(#(J80  #@   $@    &    \"     8         !0    @    !     P    $         \"0   !@           #P/P       / _        \\#\\.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    &     @    X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    <    \"    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 ':@0 X   !@    !@    @    !          4    (     0    $    !          X    P    !@    @    $          4    (     0    0    !         !  !  M8R]B#@   $@    &    \"     8         !0    @    !     P    $         \"0   !@           #P/P       / _        \\#\\.    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    &     @    X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    T    \"    #@   #@    &    \"     8         !0    @    !     0    $         \"0    @        0 'B@0 X   !H    !@    @    !          4    (     0    $    !          X    X    !@    @    $          4    (     0    8    !         !     &    +6(O,BIA   .    2     8    (    !@         %    \"     $    #     0         )    &            / _        \\#\\       #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    8    \"    #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    $     (    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  >J! #@   )     &    \"     $         !0    @    !     0    $         #@   &     &    \"     0         !0    @    !    *0    $         $    \"D   !C(\"\\@*&$J(\"@H+6(@+2!S<7)T*&)>,B M(#0J82IC*2D@+R R*F$I*0         .    2     8    (    !@         %    \"     $    #     0         )    &            / _        \\#\\       #P/PX   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    8    \"    #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !    $0    (    .    .     8    (    !@         %    \"     $    !     0         )    \"        !  ?*! #@   )     &    \"     $         !0    @    !     0    $         #@   &     &    \"     0         !0    @    !    *0    $         $    \"D   !C(\"\\@*&$J(\"@H+6(@*R!S<7)T*&)>,B M(#0J82IC*2D@+R R*F$I*0         .    2     8    (    !@         %    \"     $    #     0         )    &            / _        \\#\\       #P/PX   !H    !@    @    -          4    (    #0    $    !          8    T        W0(    !    \"     ,    2    $P   !0    5    %@   !<    8    !          .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    %     0    X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    H    !    #@   $@    &    \"     T         !0    @    &     0    $         !@   !@       #= @    $    !     @    ,    .    2     8    (    #0         %    \"     8    !     0         &    &        -T\"     0    $    %     0    X   !(    !@    @    -          4    (    !@    $    !          8    8        W0(    !     0    H    !    #@   $     &    \"     0         !0    @    !    $     $         $    !    !Q=6%D7V9C;E]S=6)T>7!E#@   '@    &    \"     0         !0    @    !    00    $         $    $$   !C+&$L8CI[>#IR96%L?\"AA/3 @/3X@>\" O/2 P*2!!3D0@*&$@+ST@,\" ]/B H>%XR*2 M(#0J82IC(#X](# I?0         .    F $   8    (     @         %    \"     $    !     0         %  0 !P    $    C    <V5T     &EN<'5T<P!C;W5N=   <F%N9V4  &5X8V5P=         X    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    ,     8    (    !@         %    \"                0         )          X    X    !@    @    &          4    (     0    $    !          D    (          ! CT .    .     8    (    !@         %    \"     $    !     0         )    \"            %E #@   #@    &    \"     8         !0    @    !     0    $         \"0    @               X    X    !@    @    &          4    (     0    $    !          D    (               .    .     8    (    !@         %    \"     $    !     0         )    \"            / _#@   #@    &    \"     8         !0    @    !     0    $         \"0    @           #P/PX    X    !@    @    &          4    (     0    $    !          D    (            \\#\\.    4 X   8    (     0         %    \"     8    !     0         .    .     8    (     @         %    \"     $          0         %  0  0    $         #@   ,@\"   &    \"     (         !0    @    !     0    $         !0 $  \\    !    AP   '!A<F5N=%]C96QL     '!A<F5N=%]G<FED     &-E;&QS             '-P;&ET7W!B         &YU;5]C96QL<P       &=R:61?:6YD97@      ')'<FED             &YE=U]C96QL7W!B     &1E;&5T95]C96QL7W!B   .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    X    !@    @    &          4    (     0    $    !          D    (               .    .     8    (    !@         %    \"     $    !     0         )    \"               #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   &@%   &    \"     (         !0    @    !     0    $         !0 $ !8    !    8 $  '-U8F=R:60                   !C;VYD                        8V]N9%]T97AT                 &-E;&Q?:6YD97@               !P87)E;G1?9W)I9               =VED=&@                      &AE:6=H=                     !G<FED7W!B                    <&)?9FQA9P                   &-O;&]R                      !C;VYD:71I;VY?=&5X=%]W:61T:   8V]N9&ET:6]N7W1E>'1?:&5I9VAT &-O;F1I=&EO;E]T97AT7W@       !C;VYD:71I;VY?=&5X=%]Y        8V]N9&ET:6]N7W1E>'1?;V9F<V5T &=R:61?<'5S:%]W:61T:          .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    .     8    (    !@         %    \"     $    !     0         )    \"               #@   #     &    \"     8         !0    @               $         \"0         .    .     8    (    !@         %    \"     $    !     0         )    \"               #@   #@    &    \"     8         !0    @    !     0    $         \"0    @               X    P    !@    @    &          4    (               !          D         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @               X    P    !@    @    &          4    (               !          D         #@   #@    &    \"     8         !0    @    !     0    $         \"0    @           !I0 X    X    !@    @    &          4    (     0    $    !          D    (            3D .    .     8    (    !@         %    \"     $    !     0         )    \"            \"1 #@   #@    &    \"     8         !0    @    !     0    $         \"0    @            D0 X    X    !@    @    &          4    (     0    $    !          D    (            -$ .    .     8    (    !@         %    \"     $    !     0         )    \"            #Y #@   /@    &    \"     (         !0    @    !     0    $         !0 $  8    !    $@   $-E;&QS $=R:60Q $=R:60R          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X   \"0 0  !@    @    \"          4    (     0    $    !          4 !  ,     0   #P   !#96QL,0        !#96QL,@        !R97-U;'0       !R97-U;'1?=&5X= !C;VQO<@              #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    ( P  !@    @    \"          4    (     0    $    !          4 !  0     0   *    !'<FED,               1W)I9#$              $=R:60R              !F=6YC=&EO;E]N86UE    9G5N8W1I;VY?:6YP=71S '-E='1I;F=S          !C:&5C:V5D            ;W!E;@               &9I9P                !M=6QT:5]M;V1E        #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    ,     8    (    !@         %    \"                0         )          X    P    !@    @    &          4    (               !          D         #@   #     &    \"     8         !0    @               $         \"0         .    B     8    (    \"0         %    \"     $   !8     0         \"    6      !24T     #@   $@    &    \"     (         !0    @    !     0    $         !0 $  4    !    !0   $U#3U,     #@         ";
        InputStream decoder = new SimulinkDecoder(input);

        byte[] buf = new byte[20144];

        int read = decoder.read(buf);

        assertThat(read, is(20144));

        boolean atEnd = false;
        try {
            decoder.read();
        } catch (EOFException ex) {
            atEnd = true;
        }
        assertThat(atEnd, is(true));

        byte[] expectedBuf = new byte[20144];
        SimulinkMatTest.class.getResourceAsStream("/simulink_tet_out.mat").read(expectedBuf);

        assertThat(buf, is(expectedBuf));
    }

    private File fileFromStream(String location) throws IOException
    {
        String outname = location.replace("/", "_");
        File f = folder.newFile(outname);
        InputStream stream = SimulinkMatTest.class.getResourceAsStream(location);
        OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
        byte[] buffer = new byte[1024];
        int read = 0;
        while((read = stream.read(buffer)) != -1){fos.write(buffer, 0, read);}
        fos.flush(); fos.close();
        return f;
    }
}

