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
import { useGetSelectedShelf } from "@/hooks/store";

interface JournalRowProps {
  id: string;
  title: string;
}

const JournalRow = (props: JournalRowProps) => {
  const router = useRouter();

  const { title, id } = props;

  return (
    <TableRow
      className="hover:bg-muted hover:cursor-pointer"
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
  const { data: getShelvesResponse, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useGetSelectedShelf(getShelvesResponse?.shelves);

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
                <JournalRow key={index} id={journal.id} title={journal.title} />
              );
            })
          : null}
      </TableBody>
    </Table>
  );
}
