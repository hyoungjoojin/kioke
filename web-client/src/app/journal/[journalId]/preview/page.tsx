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

enum JOURNAL_PREVIEW_OPTION {
  LIST = "list",
  CALENDAR = "calendar",
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
              <div className="rounded-xl w-[30rem]">
                <Input placeholder="Invite people by email" />
                <div className="h-72 flex items-center justify-center">
                  <div className="font-bold">
                    Share your journey with your friends and family.
                  </div>
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
