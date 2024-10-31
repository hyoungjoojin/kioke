import { GetShelfResponse } from "@/types/api/shelf";
import { NextRequest, NextResponse } from "next/server";

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ id: string }> },
): Promise<NextResponse<GetShelfResponse>> {
  const id = (await params).id;

  return NextResponse.json(
    {
      id: id,
      title: "2024's Bookshelf",
      jids: [
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "a",
        "b",
        "c",
        "d",
        "e",
      ],
    },
    {
      status: 200,
    },
  );
}
