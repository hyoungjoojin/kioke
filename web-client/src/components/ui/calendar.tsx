"use client";

import { useCallback, useState } from "react";
import { Button } from "./button";
import moment from "moment";
import ReactCalendar, { TileContentFunc } from "react-calendar";
import { ChevronLeft, ChevronRight, CircleDot } from "lucide-react";
import { groupBy } from "lodash";
import {
  HoverCard,
  HoverCardContent,
  HoverCardTrigger,
} from "@/components/ui/hover-card";
import { useRouter } from "next/navigation";
import { Journal } from "@/types/primitives/journal";
import { cn } from "@/lib/utils";

interface CalendarProps {
  journal: Journal;
}

export default function Calendar({ journal }: CalendarProps) {
  const router = useRouter();

  const [selectedDate, setSelectedDate] = useState<Date>(new Date());

  const pagesGroupedByDate = groupBy(journal.pages, (page) => {
    return moment(page.createdAt).format("YYMMDD");
  });

  const today = new Date();

  const tileContent: TileContentFunc = useCallback(
    ({ date }) => {
      const pagesByDate = pagesGroupedByDate[moment(date).format("YYMMDD")];

      const isCurrentMonth = date.getMonth() === selectedDate.getMonth();
      const isFirstDate = date.getDate() === 1;
      const isToday =
        date.getUTCFullYear() === today.getUTCFullYear() &&
        date.getMonth() === today.getMonth() &&
        date.getDate() === today.getDate();

      return (
        <div className="relative w-full h-24 flex items-center justify-center border border-gray border-collapse cursor-default">
          <span
            className={cn(
              "absolute top-2 right-2 text-xs font-bold",
              !isCurrentMonth ? "text-gray-400" : "",
            )}
          >
            <div
              className={cn(
                "rounded-full p-1",
                isToday ? "bg-red-500 text-white" : "",
              )}
            >
              {isFirstDate ? moment(date).format("MMMM D") : date.getDate()}
            </div>
          </span>

          <div>
            {pagesByDate &&
              pagesByDate.map((page) => {
                return (
                  <HoverCard key={page.pageId} openDelay={1}>
                    <HoverCardTrigger>
                      <CircleDot
                        size={16}
                        onClick={(event) => {
                          router.push(
                            `/journal/${journal.journalId}/${page.pageId}`,
                          );
                          event.stopPropagation();
                        }}
                      />
                    </HoverCardTrigger>

                    <HoverCardContent>
                      <span key={page.pageId}>
                        {page.title === "" ? "Untitled" : page.title}
                      </span>
                    </HoverCardContent>
                  </HoverCard>
                );
              })}
          </div>
        </div>
      );
    },
    [today, selectedDate, setSelectedDate, journal],
  );

  return (
    <div>
      <div className="w-full flex justify-between items-center font-semibold mb-7 px-5">
        <h1>{moment(selectedDate).format("MMMM, YYYY")}</h1>
        <div className="flex items-center">
          <Button
            variant="ghost"
            className="p-0"
            onClick={() => {
              setSelectedDate((date) => {
                return moment(date).subtract(1, "month").toDate();
              });
            }}
          >
            <ChevronLeft size={18} />
          </Button>

          <Button
            variant="ghost"
            onClick={() => {
              setSelectedDate(new Date());
            }}
          >
            Today
          </Button>

          <Button
            variant="ghost"
            className="p-0"
            onClick={() => {
              setSelectedDate((date) => {
                return moment(date).add(1, "month").toDate();
              });
            }}
          >
            <ChevronRight size={18} />
          </Button>
        </div>
      </div>

      <ReactCalendar
        value={selectedDate}
        onClickDay={(value) => {
          setSelectedDate(value);
        }}
        showNavigation={false}
        nextLabel={null}
        prevLabel={null}
        next2Label={null}
        prev2Label={null}
        tileContent={tileContent}
        locale="en"
      />
    </div>
  );
}
