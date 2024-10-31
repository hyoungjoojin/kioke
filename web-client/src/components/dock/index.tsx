import { FaUserFriends } from "react-icons/fa";
import { PiBooksFill } from "react-icons/pi";
import { FaRegPenToSquare } from "react-icons/fa6";
import { MdOutlineSearch } from "react-icons/md";

export default function Dock() {
  return (
    <div className="flex justify-center mb-10">
      <div className="bg-white rounded-full shadow-lg p-4 flex items-center justify-around w-1/2 max-w-md ">
        <FaUserFriends size={32} color="black" title="Social" />
        <PiBooksFill size={32} color="black" title="Shelf" />
        <MdOutlineSearch size={32} color="black" title="Search" />
        <FaRegPenToSquare size={28} color="black" title="Write" />
      </div>
    </div>
  );
}
