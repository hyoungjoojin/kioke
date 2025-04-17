"use client";

import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  useJournalQuery,
  useToggleJournalBookmarkMutation,
  useUpdateJournalMutation,
} from "@/hooks/query/journal";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Ellipsis,
  UserRoundPlus,
  Trash2,
  Heart,
  HomeIcon,
  Plus,
  List,
  CalendarRange,
  TextIcon,
} from "lucide-react";
import { useParams, useRouter } from "next/navigation";
import { useCreatePageMutation } from "@/hooks/query/page";
import { Input } from "@/components/ui/input";
import { ChangeEvent, useMemo, useRef, useState } from "react";
import { emailSchema } from "@/utils/schema";
import { searchUser } from "@/app/api/user";
import { SearchUserResponseBody } from "@/types/server/user";
import debounce from "lodash/debounce";
import { RingSpinner } from "@/components/ui/spinner";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Role, Roles } from "@/constants/role";
import { shareJournal } from "@/app/api/journal";
import { getQueryClient } from "@/components/providers/QueryProvider";
import EditableTitle from "@/components/features/editor/EditableTitle";
import Calendar from "@/components/ui/calendar/calendar";

enum JOURNAL_PREVIEW_OPTION {
  LIST = "list",
  CALENDAR = "calendar",
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
    icon: <List size={16} />,
    title: "List",
  },
  [JOURNAL_PREVIEW_OPTION.CALENDAR]: {
    icon: <CalendarRange size={16} />,
    title: "Calendar",
  },
};

export default function JournalPreview() {
  const router = useRouter();
  const { journalId } = useParams<{ journalId: string }>();
  const { data: journal, isLoading } = useJournalQuery(journalId);
  const { mutate: createPage } = useCreatePageMutation(journalId);
  const { mutate: updateJournal } = useUpdateJournalMutation(journalId);
  const { mutate: toggleJournalBookmark } =
    useToggleJournalBookmarkMutation(journalId);

  const queryClient = getQueryClient();

  const userSearchInputRef = useRef<HTMLInputElement | null>(null);
  const [userSearchQuery, setUserSearchQuery] = useState("");
  const [inviteeRole, setInviteeRole] = useState<Role | null>(null);
  const [userSearchState, setUserSearchState] = useState<UserSearchState>({
    isFocused: false,
    isValidEmail: false,
    isLoading: false,
    user: null,
  });

  const searchUserInputChangeHandler = useMemo(
    () =>
      debounce(async (event: ChangeEvent<HTMLInputElement>) => {
        const query = event.target.value;

        if (userSearchState.isFocused && query === "") {
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
    [],
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
        queryClient.invalidateQueries({ queryKey: ["journals", journalId] });
      }
    } catch (e) {
      console.log(e);
    }
  };

  if (isLoading || !journal) {
    return "Loading...";
  }

  return (
    <>
      <header className="flex justify-between items-center w-full h-10 mt-2 px-7">
        <div>
          <Button
            variant="link"
            onClick={() => {
              router.push("/");
            }}
          >
            <HomeIcon size={18} />
          </Button>
        </div>

        <div className="flex items-center">
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="sm">
                <UserRoundPlus size={18} />
                <p className="text-sm font-semibold text-gray-600">Share</p>
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
              align="end"
            >
              <div className="rounded-xl w-[28rem]">
                <div className="relative">
                  <div className="flex">
                    <Input
                      ref={userSearchInputRef}
                      placeholder="Invite people by email"
                      value={userSearchQuery}
                      onChange={(event) => {
                        setUserSearchQuery(event.target.value);
                        searchUserInputChangeHandler(event);
                      }}
                      onKeyDown={(event) => {
                        if (event.key === "Enter") {
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
                      value={inviteeRole?.toString() ?? ""}
                      onValueChange={(role) => {
                        setInviteeRole(Role[role as keyof typeof Role]);
                      }}
                    >
                      <SelectTrigger className="ml-1 text-xs w-28">
                        <SelectValue placeholder="Select Role" />
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
                      className="ml-1"
                      onClick={searchUserInputSubmitHandler}
                    >
                      Invite
                    </Button>
                  </div>

                  {userSearchState.isFocused && (
                    <div className="absolute w-full mt-2 px-2">
                      <div className="flex justify-between items-center">
                        <div className="text-sm">
                          Invite someone to join your journey.
                          {!userSearchState.isValidEmail && (
                            <p className="text-xs">
                              Enter an email address to invite someone new.
                            </p>
                          )}
                        </div>

                        <RingSpinner loading={userSearchState.isLoading} />
                      </div>

                      {!userSearchState.isLoading &&
                        userSearchState.isValidEmail &&
                        userSearchState.user && (
                          <div className="mt-5 flex">
                            <Avatar className="mr-2 h-8 w-8">
                              <AvatarFallback>{`${userSearchState.user.firstName[0]}${userSearchState.user.lastName[0]}`}</AvatarFallback>
                            </Avatar>
                            <div>
                              <p className="text-sm">
                                {userSearchState.user.firstName}{" "}
                                {userSearchState.user.lastName}
                              </p>
                              <p className="text-xs">
                                {userSearchState.user.email}
                              </p>
                            </div>
                          </div>
                        )}
                    </div>
                  )}
                </div>

                <div className="h-[32rem] flex items-center justify-center">
                  {!userSearchState.isFocused && (
                    <div className="w-full self-start">
                      {journal.users.length ? (
                        journal.users.map((user, index) => {
                          return (
                            <div
                              key={index}
                              className="w-4/5 mx-auto flex justify-between items-center py-2"
                            >
                              <div className="flex">
                                <Avatar className="mr-2 h-8 w-8">
                                  <AvatarFallback>{`${user.firstName[0]}${user.lastName[0]}`}</AvatarFallback>
                                </Avatar>
                                <div>
                                  <p className="text-sm">
                                    {user.firstName} {user.lastName}
                                  </p>
                                  <p className="text-xs">{user.email}</p>
                                </div>
                              </div>

                              <p>{Roles[user.role].title}</p>
                            </div>
                          );
                        })
                      ) : (
                        <>
                          <p className="font-bold text-center">
                            Share this moment.
                          </p>
                          <p className="text-sm text-center">
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
            {journal.bookmarked ? (
              <Heart size={20} fill="black" />
            ) : (
              <Heart size={20} />
            )}
          </div>

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="icon">
                <Ellipsis size={18} />
              </Button>
            </DropdownMenuTrigger>

            <DropdownMenuContent align="end">
              <DropdownMenuItem>
                <div className="flex items-center text-red-500">
                  <Trash2 size={16} className="mx-1" />
                  Delete Journal
                </div>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </header>

      <main className="w-4/5 mx-auto">
        <EditableTitle
          content={journal.title}
          onSubmit={(title) => {
            if (journal.title !== title) {
              updateJournal({ title });
            }
          }}
        />
        <p className="mt-5 italic">{journal.description}</p>

        <h2 className="text-xl mt-12">Pages</h2>

        <Tabs defaultValue={JOURNAL_PREVIEW_OPTION.LIST} className="mt-2">
          <div className="flex justify-between items-center">
            <TabsList className="grid grid-cols-2">
              {Object.entries(JournalPreviewOptionValues).map(
                ([key, value]) => {
                  return (
                    <TabsTrigger
                      key={key}
                      value={key}
                      className="w-full flex justify-center px-4"
                    >
                      {value.icon}
                      <p className="ml-1">{value.title}</p>
                    </TabsTrigger>
                  );
                },
              )}
            </TabsList>

            <Button
              variant="ghost"
              size="sm"
              onClick={() => {
                createPage();
              }}
            >
              <Plus size={16} />
              <p className="text-xs font-semibold text-gray-600">Add Page</p>
            </Button>
          </div>

          <TabsContent value={JOURNAL_PREVIEW_OPTION.LIST}>
            {journal.pages.map((page, index) => {
              return (
                <div
                  key={index}
                  className="flex justify-between items-center my-2"
                >
                  <p
                    onClick={() => {
                      router.push(`/journal/${journalId}/${page.pageId}`);
                    }}
                    className="flex items-center border-b border-b-transparent hover:border-b-black hover:cursor-pointer"
                  >
                    <TextIcon size={16} className="mr-1" />
                    {page.title === "" ? "Untitled" : page.title}
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
      </main>
    </>
  );
}
