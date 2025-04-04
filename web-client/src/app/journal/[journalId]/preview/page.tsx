"use client";

import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useJournalQuery } from "@/hooks/query/journal";
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
} from "lucide-react";
import { useParams, useRouter } from "next/navigation";
import { useCreatePageMutation } from "@/hooks/query/page";
import { Input } from "@/components/ui/input";
import { ChangeEvent, useEffect, useMemo, useState } from "react";
import { emailSchema } from "@/utils/schema";
import { searchUser } from "@/app/api/user";
import { SearchUserResponseBody } from "@/types/server/user";
import debounce from "lodash/debounce";
import { RingSpinner } from "@/components/ui/spinner";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";

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

  const [userSearchState, setUserSearchState] = useState<UserSearchState>({
    isFocused: false,
    isValidEmail: false,
    isLoading: false,
    user: null,
  });

  useEffect(() => {
    const keydownEventHandler = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        if (userSearchState.isFocused) {
          setUserSearchState((state) => ({ ...state, on: false }));
          // TODO: Stop propagation - don't close dropdown
        }
      }
    };

    window.addEventListener("keydown", keydownEventHandler);

    return () => {
      window.removeEventListener("keydown", keydownEventHandler);
    };
  }, [userSearchState]);

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

  const searchUserInputSubmitHandler = () => {
    if (!userSearchState.isValidEmail) {
      return;
    }

    // TODO: Actually send the request for sharing
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

            <DropdownMenuContent align="end">
              <div className="rounded-xl w-[28rem]">
                <div className="relative">
                  <Input
                    placeholder="Invite people by email"
                    onChange={searchUserInputChangeHandler}
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
                    <div className="w-ful">
                      <p className="font-bold text-center">
                        Share this moment.
                      </p>
                      <p className="text-sm text-center">
                        Invite your friends and family to your journey.
                      </p>
                    </div>
                  )}
                </div>
              </div>
            </DropdownMenuContent>
          </DropdownMenu>

          <Heart size={18} />

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
        <h1 className="text-3xl font-semibold mt-8">{journal.title}</h1>
        <p className="mt-5 italic">{journal.description}</p>

        <h2 className="text-xl mt-12">Pages</h2>

        <Tabs defaultValue={JOURNAL_PREVIEW_OPTION.LIST} className="mt-2">
          <div className="flex justify-between items-center">
            <TabsList className="grid grid-cols-2 w-[220px]">
              {Object.entries(JournalPreviewOptionValues).map(
                ([key, value]) => {
                  return (
                    <TabsTrigger key={key} value={key}>
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
              return <div key={index}>{page.date}</div>;
            })}
          </TabsContent>

          <TabsContent value={JOURNAL_PREVIEW_OPTION.CALENDAR}>
            NOT IMPLEMENTED YET
          </TabsContent>
        </Tabs>
      </main>
    </>
  );
}
