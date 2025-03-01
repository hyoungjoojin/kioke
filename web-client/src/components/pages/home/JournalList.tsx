"use client";

import {
  Table,
  TableBody,
  TableHead,
  TableHeader,
  TableRow,
  TableCell,
} from "@/components/ui/table";
import { useRouter } from "next/navigation";
import { useShelvesQuery } from "@/hooks/query";
import { useSelectedShelf } from "@/hooks/store";
import { Ellipsis, Trash2 } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

interface JournalListItemProps {
  id: string;
  title: string;
}

const JournalListItem = ({ id, title }: JournalListItemProps) => {
  const router = useRouter();

  return (
    <TableRow
      changeOnHover
      onClick={() => {
        router.push(`/journal/${id}/preview`);
      }}
    >
      <TableCell>
        <p className="select-none">{title}</p>
      </TableCell>

      <TableCell>
        <p className="select-none">{id}</p>
      </TableCell>

      <TableCell
        onClick={(e) => {
          e.stopPropagation();
        }}
      >
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <div>
              <Ellipsis size={15} />
            </div>
          </DropdownMenuTrigger>
          <DropdownMenuContent loop align="center">
            <DropdownMenuItem>
              <div className="flex items-center text-red-500">
                <Trash2 size={16} className="mx-1" />
                Delete Journal
              </div>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </TableCell>
    </TableRow>
  );
};

export default function JournalList() {
  const { data, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Title</TableHead>
          <TableHead>Description</TableHead>
          <TableHead></TableHead>
        </TableRow>
      </TableHeader>

      <TableBody>
        {!isLoading && !isError && selectedShelf
          ? selectedShelf.journals.map((journal, index) => {
              return (
                <JournalListItem
                  key={index}
                  id={journal.id}
                  title={journal.title}
                />
              );
            })
          : null}
      </TableBody>
    </Table>
  );
}
