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
      <TableCell>{title}</TableCell>
      <TableCell>{id}</TableCell>
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
