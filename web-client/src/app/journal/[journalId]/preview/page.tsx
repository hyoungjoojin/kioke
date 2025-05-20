'use client';

import { shareJournal } from '@/app/api/journal';
import { searchUser } from '@/app/api/user';
import EditableTitle from '@/components/features/editor/EditableTitle';
import { getQueryClient } from '@/components/providers/QueryProvider';
import KiokeSidebar from '@/components/sidebar/KiokeSidebar';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import Calendar from '@/components/ui/calendar/calendar';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { Input } from '@/components/ui/input';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { SidebarTrigger } from '@/components/ui/sidebar';
import { RingSpinner } from '@/components/ui/spinner';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { ErrorCode } from '@/constants/errors';
import { Role, Roles } from '@/constants/role';
import View from '@/constants/view';
import {
  useJournalQuery,
  useToggleJournalBookmarkMutation,
  useUpdateJournalMutation,
} from '@/hooks/query/journal';
import { useCreatePageMutation } from '@/hooks/query/page';
import { useCurrentView } from '@/hooks/store/view';
import { SearchUserResponseBody } from '@/types/server/user';
import { emailSchema } from '@/utils/schema';
import debounce from 'lodash/debounce';
import { useSession } from 'next-auth/react';
import { notFound, redirect, useParams, useRouter } from 'next/navigation';
import { ChangeEvent, useEffect, useMemo, useRef, useState } from 'react';

enum JOURNAL_PREVIEW_OPTION {
  LIST = 'list',
  CALENDAR = 'calendar',
}

interface UserSearchState {
  isFocused: boolean;
  isValidEmail: boolean;
  isLoading: boolean;
  user: SearchUserResponseBody | null;
}

const JournalPreviewOptionValues: {
  [key in JOURNAL_PREVIEW_OPTION]: { icon: React.ReactNode; title: string };
} = {
  [JOURNAL_PREVIEW_OPTION.LIST]: {
    icon: null,
    title: 'List',
  },
  [JOURNAL_PREVIEW_OPTION.CALENDAR]: {
    icon: null,
    title: 'Calendar',
  },
};

export default function JournalPreview() {
  const session = useSession();
  const router = useRouter();
  const { journalId } = useParams<{ journalId: string }>();
  const { setCurrentView } = useCurrentView();

  useEffect(() => {
    setCurrentView(View.SHELF);
  }, []);

  const {
    data: journal,
    isLoading,
    isError,
    error,
  } = useJournalQuery(journalId);
  const { mutate: createPage } = useCreatePageMutation(journalId);
  const { mutate: updateJournal } = useUpdateJournalMutation(journalId);
  const { mutate: toggleJournalBookmark } =
    useToggleJournalBookmarkMutation(journalId);

  const queryClient = getQueryClient();

  const userSearchInputRef = useRef<HTMLInputElement | null>(null);
  const [userSearchQuery, setUserSearchQuery] = useState('');
  const [inviteeRole, setInviteeRole] = useState<Role | null>(null);
  const [userSearchState, setUserSearchState] = useState<UserSearchState>({
    isFocused: false,
    isValidEmail: false,
    isLoading: false,
    user: null,
  });

  const user = session?.data?.user;
  if (!user) {
    redirect('/auth/login');
  }

  const searchUserInputChangeHandler = useMemo(
    () =>
      debounce(async (event: ChangeEvent<HTMLInputElement>) => {
        const query = event.target.value;

        if (userSearchState.isFocused && query === '') {
          setUserSearchState((state) => ({
            ...state,
            isFocused: false,
          }));
          return;
        }

        const queryParseResult = emailSchema.safeParse(query);
        if (!queryParseResult.success) {
          setUserSearchState((state) => ({
            ...state,
            isFocused: true,
            isValidEmail: false,
          }));
          return;
        }

        setUserSearchState((state) => ({
          ...state,
          isFocused: true,
          isLoading: true,
          isValidEmail: true,
        }));

        const email = queryParseResult.data;
        const user = await searchUser(email);

        setUserSearchState((state) => ({
          ...state,
          isLoading: false,
          user: user.userId ? user : null,
        }));
      }, 250),
    [userSearchState.isFocused],
  );

  const searchUserInputSubmitHandler = async () => {
    setUserSearchState((state) => ({
      ...state,
      isFocused: false,
    }));

    if (userSearchInputRef.current) {
      userSearchInputRef.current.blur();
    }

    const queryParseResult = emailSchema.safeParse(userSearchQuery);
    if (!queryParseResult.success || !inviteeRole) {
      return;
    }

    const email = queryParseResult.data;
    const user = await searchUser(email);

    try {
      if (user.userId) {
        await shareJournal(user.userId, journalId, inviteeRole);
        queryClient.invalidateQueries({ queryKey: ['journals', journalId] });
      }
    } catch (e) {
      console.log(e);
    }
  };

  if (isError && error && error.message.includes(ErrorCode.JOURNAL_NOT_FOUND)) {
    notFound();
  }

  if (isLoading || !journal) {
    return 'Loading...';
  }

  return (
    <>
      <aside>
        <KiokeSidebar user={user} />
      </aside>

      <header className='absolute w-full pt-3 px-3 flex justify-between'>
        <div className='flex items-center'>
          <SidebarTrigger />

          <Button variant='ghost' className='hover:cursor-not-allowed'>
            <span>Back to shelf</span>
          </Button>
        </div>

        <div className='flex items-center'>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='ghost' size='sm'>
                <p className='text-sm font-semibold text-gray-600'>Share</p>
              </Button>
            </DropdownMenuTrigger>

            <DropdownMenuContent
              onEscapeKeyDown={(e) => {
                if (userSearchState.isFocused) {
                  if (userSearchInputRef.current) {
                    userSearchInputRef.current.blur();
                  }

                  setUserSearchState((state) => ({
                    ...state,
                    isFocused: false,
                  }));
                  e.preventDefault();
                }
              }}
              align='end'
            >
              <div className='rounded-xl w-[28rem]'>
                <div className='relative'>
                  <div className='flex'>
                    <Input
                      ref={userSearchInputRef}
                      placeholder='Invite people by email'
                      value={userSearchQuery}
                      onChange={(event) => {
                        setUserSearchQuery(event.target.value);
                        searchUserInputChangeHandler(event);
                      }}
                      onKeyDown={(event) => {
                        if (event.key === 'Enter') {
                          searchUserInputSubmitHandler();
                        }
                      }}
                      onFocus={() => {
                        setUserSearchState((state) => ({
                          ...state,
                          isFocused: true,
                        }));
                      }}
                    />

                    <Select
                      value={inviteeRole?.toString() ?? ''}
                      onValueChange={(role) => {
                        setInviteeRole(Role[role as keyof typeof Role]);
                      }}
                    >
                      <SelectTrigger className='ml-1 text-xs w-28'>
                        <SelectValue placeholder='Select Role' />
                      </SelectTrigger>

                      <SelectContent>
                        {Object.values(Role).map((role, index) => (
                          <SelectItem key={index} value={role}>
                            <p>{Roles[role].title}</p>
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>

                    <Button
                      className='ml-1'
                      onClick={searchUserInputSubmitHandler}
                    >
                      Invite
                    </Button>
                  </div>

                  {userSearchState.isFocused && (
                    <div className='absolute w-full mt-2 px-2'>
                      <div className='flex justify-between items-center'>
                        <div className='text-sm'>
                          Invite someone to join your journey.
                          {!userSearchState.isValidEmail && (
                            <p className='text-xs'>
                              Enter an email address to invite someone new.
                            </p>
                          )}
                        </div>

                        <RingSpinner loading={userSearchState.isLoading} />
                      </div>

                      {!userSearchState.isLoading &&
                        userSearchState.isValidEmail &&
                        userSearchState.user && (
                          <div className='mt-5 flex'>
                            <Avatar className='mr-2 h-8 w-8'>
                              <AvatarFallback>{`${userSearchState.user.firstName[0]}${userSearchState.user.lastName[0]}`}</AvatarFallback>
                            </Avatar>
                            <div>
                              <p className='text-sm'>
                                {userSearchState.user.firstName}{' '}
                                {userSearchState.user.lastName}
                              </p>
                              <p className='text-xs'>
                                {userSearchState.user.email}
                              </p>
                            </div>
                          </div>
                        )}
                    </div>
                  )}
                </div>

                <div className='h-[32rem] flex items-center justify-center'>
                  {!userSearchState.isFocused && (
                    <div className='w-full self-start'>
                      {journal.users ? (
                        journal.users.map((user, index) => {
                          // TODO - UserDTO here is from the user service; cannot use generated types.
                          return (
                            <div
                              key={index}
                              className='w-4/5 mx-auto flex justify-between items-center py-2'
                            >
                              {/* <div className='flex'> */}
                              {/*   <Avatar className='mr-2 h-8 w-8'> */}
                              {/*     <AvatarFallback>{`AA`}</AvatarFallback> */}
                              {/*   </Avatar> */}
                              {/*   <div> */}
                              {/*     <p className='text-sm'> */}
                              {/*     </p> */}
                              {/*     <p className='text-xs'>{tem}</p> */}
                              {/*   </div> */}
                              {/* </div> */}

                              {/* <p>{Roles[user.role].title}</p> */}
                            </div>
                          );
                        })
                      ) : (
                        <>
                          <p className='font-bold text-center'>
                            Share this moment.
                          </p>
                          <p className='text-sm text-center'>
                            Invite your friends and family to your journey.
                          </p>
                        </>
                      )}
                    </div>
                  )}
                </div>
              </div>
            </DropdownMenuContent>
          </DropdownMenu>

          <div
            onClick={(e) => {
              toggleJournalBookmark(!journal.bookmarked);
              e.stopPropagation();
            }}
          >
            {journal.bookmarked ? null : null}
          </div>

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant='ghost' size='icon'></Button>
            </DropdownMenuTrigger>

            <DropdownMenuContent align='end'>
              <DropdownMenuItem>
                <div className='flex items-center text-red-500'>
                  Delete Journal
                </div>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </header>

      <main className='w-full pt-16'>
        <section className='px-16'>
          <EditableTitle
            content={journal.title}
            onSubmit={(title) => {
              if (journal.title !== title) {
                updateJournal({ title });
              }
            }}
          />
          <p className='mt-5 italic'>{journal.description}</p>

          <h2 className='text-xl mt-12'>Pages</h2>

          <Tabs defaultValue={JOURNAL_PREVIEW_OPTION.LIST} className='mt-2'>
            <div className='flex justify-between items-center'>
              <TabsList className='grid grid-cols-2'>
                {Object.entries(JournalPreviewOptionValues).map(
                  ([key, value]) => {
                    return (
                      <TabsTrigger
                        key={key}
                        value={key}
                        className='w-full flex justify-center px-4'
                      >
                        {value.icon}
                        <p className='ml-1'>{value.title}</p>
                      </TabsTrigger>
                    );
                  },
                )}
              </TabsList>

              <Button
                variant='ghost'
                size='sm'
                onClick={() => {
                  createPage();
                }}
              >
                <p className='text-xs font-semibold text-gray-600'>Add Page</p>
              </Button>
            </div>

            <TabsContent value={JOURNAL_PREVIEW_OPTION.LIST}>
              {journal.pages.map((page, index) => {
                return (
                  <div
                    key={index}
                    className='flex justify-between items-center my-2'
                  >
                    <p
                      onClick={() => {
                        router.push(`/journal/${journalId}/${page.pageId}`);
                      }}
                      className='flex items-center border-b border-b-transparent hover:border-b-black hover:cursor-pointer'
                    >
                      {page.title === '' ? 'Untitled' : page.title}
                    </p>
                    <p>{page.createdAt.toDateString()}</p>
                  </div>
                );
              })}
            </TabsContent>

            <TabsContent value={JOURNAL_PREVIEW_OPTION.CALENDAR}>
              <Calendar journal={journal} />
            </TabsContent>
          </Tabs>
        </section>
      </main>
    </>
  );
}
