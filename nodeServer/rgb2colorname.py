#!/usr/bin/env python
# rgb2colorname.py
# by wilsonmar@gmail.com, ayush.original@gmail.com, https://github.com/paarthneekhara
# Usage: python rgb2colorname.py
# To ensure this program has no external dependencies, 
# an array and dictionary is used in place of I/O from input reference files.
# Explained in https://github.com/jetbloom/rgb2colorname/blob/master/README.md

# import Algorithmia
import numpy as np
from scipy import spatial


# TODO: define function for use in Algorithmia.com or other API:
#def find_nearest_vector(array, value):
#  # http://docs.scipy.org/doc/numpy/reference/generated/numpy.linalg.norm.html
#  idx = np.array([np.linalg.norm(x+y) for (x,y,z) in array-value]).argmin()
#  return array[idx]

# NO import importlib & import_module('rgbcsv2rgbarray.py.txt') to avoid external dependencies.
#moduleName='rgbcsv2rgbarray.py.txt'
# mport_module(moduleName) 
#### Paste in contents of rgb_combined_v01.csv.txt below: ###
# 2016-08-31-07:53 (local time) rgbcsv2rgbarray.py START: outrowcount=570.
def color_detect(red, green, blue):
    RGB = np.array([ \
     [0,0,0] \
    ,[0,0,128] \
    ,[0,0,139] \
    ,[0,0,139] \
    ,[0,0,205] \
    ,[0,0,205] \
    ,[0,0,238] \
    ,[0,0,255] \
    ,[0,0,255] \
    ,[0,56,102] \
    ,[0,100,0] \
    ,[0,104,139] \
    ,[0,107,60] \
    ,[0,128,0] \
    ,[0,128,0] \
    ,[0,128,128] \
    ,[0,134,139] \
    ,[0,139,0] \
    ,[0,139,69] \
    ,[0,139,139] \
    ,[0,139,139] \
    ,[0,154,205] \
    ,[0,155,145] \
    ,[0,178,238] \
    ,[0,191,255] \
    ,[0,191,255] \
    ,[0,197,205] \
    ,[0,205,0] \
    ,[0,205,102] \
    ,[0,205,205] \
    ,[0,206,209] \
    ,[0,229,238] \
    ,[0,238,0] \
    ,[0,238,118] \
    ,[0,238,238] \
    ,[0,245,255] \
    ,[0,250,154] \
    ,[0,255,0] \
    ,[0,255,0] \
    ,[0,255,0] \
    ,[0,255,0] \
    ,[0,255,127] \
    ,[0,255,127] \
    ,[0,255,255] \
    ,[0,255,255] \
    ,[0,255,255] \
    ,[2,102,102] \
    ,[3,0,107] \
    ,[3,3,3] \
    ,[3,70,37] \
    ,[3,82,43] \
    ,[5,5,5] \
    ,[5,73,39] \
    ,[8,8,8] \
    ,[10,10,10] \
    ,[12,27,139] \
    ,[13,13,13] \
    ,[15,15,15] \
    ,[16,78,139] \
    ,[17,96,0] \
    ,[18,18,18] \
    ,[20,20,20] \
    ,[22,36,66] \
    ,[23,23,23] \
    ,[23,77,132] \
    ,[24,63,114] \
    ,[24,116,205] \
    ,[25,25,112] \
    ,[26,26,26] \
    ,[28,28,28] \
    ,[28,134,238] \
    ,[30,144,255] \
    ,[30,144,255] \
    ,[31,31,31] \
    ,[32,178,170] \
    ,[33,33,33] \
    ,[34,139,34] \
    ,[36,36,36] \
    ,[38,38,38] \
    ,[39,64,139] \
    ,[41,41,41] \
    ,[43,43,43] \
    ,[46,46,46] \
    ,[46,139,87] \
    ,[46,139,87] \
    ,[47,79,79] \
    ,[48,48,48] \
    ,[50,205,50] \
    ,[51,51,51] \
    ,[54,54,54] \
    ,[54,100,139] \
    ,[56,56,56] \
    ,[58,95,205] \
    ,[59,59,59] \
    ,[60,179,113] \
    ,[61,61,61] \
    ,[63,96,77] \
    ,[64,64,64] \
    ,[64,224,208] \
    ,[65,105,225] \
    ,[66,66,66] \
    ,[67,110,238] \
    ,[67,205,128] \
    ,[68,124,51] \
    ,[69,69,69] \
    ,[69,139,0] \
    ,[69,139,116] \
    ,[70,104,53] \
    ,[70,130,180] \
    ,[71,60,139] \
    ,[71,71,71] \
    ,[72,61,139] \
    ,[72,118,255] \
    ,[72,209,204] \
    ,[74,74,74] \
    ,[74,112,139] \
    ,[75,0,130] \
    ,[77,77,77] \
    ,[78,238,148] \
    ,[79,79,79] \
    ,[79,148,205] \
    ,[82,82,82] \
    ,[82,139,139] \
    ,[83,134,139] \
    ,[84,84,84] \
    ,[84,127,116] \
    ,[84,139,84] \
    ,[84,255,159] \
    ,[85,26,139] \
    ,[85,107,47] \
    ,[87,87,87] \
    ,[89,89,89] \
    ,[91,0,1] \
    ,[92,92,92] \
    ,[92,172,238] \
    ,[93,71,139] \
    ,[94,94,94] \
    ,[95,158,160] \
    ,[96,123,139] \
    ,[97,97,97] \
    ,[98,139,73] \
    ,[99,99,99] \
    ,[99,184,255] \
    ,[100,149,237] \
    ,[102,51,153] \
    ,[102,139,139] \
    ,[102,205,0] \
    ,[102,205,170] \
    ,[102,205,170] \
    ,[104,34,139] \
    ,[104,131,139] \
    ,[104,157,97] \
    ,[105,89,205] \
    ,[105,105,105] \
    ,[105,105,105] \
    ,[105,139,34] \
    ,[105,139,105] \
    ,[106,90,205] \
    ,[107,107,107] \
    ,[107,142,35] \
    ,[108,123,139] \
    ,[108,166,205] \
    ,[110,110,110] \
    ,[110,123,139] \
    ,[110,139,61] \
    ,[111,157,85] \
    ,[112,112,112] \
    ,[112,128,144] \
    ,[115,115,115] \
    ,[117,117,117] \
    ,[118,238,0] \
    ,[118,238,198] \
    ,[119,136,153] \
    ,[119,168,112] \
    ,[120,120,120] \
    ,[120,175,110] \
    ,[121,205,205] \
    ,[122,55,139] \
    ,[122,55,139] \
    ,[122,103,238] \
    ,[122,122,122] \
    ,[122,139,139] \
    ,[122,197,205] \
    ,[123,104,238] \
    ,[124,205,124] \
    ,[124,252,0] \
    ,[125,38,205] \
    ,[125,125,125] \
    ,[126,181,114] \
    ,[126,192,238] \
    ,[127,127,127] \
    ,[127,255,0] \
    ,[127,255,0] \
    ,[127,255,212] \
    ,[127,255,212] \
    ,[128,0,0] \
    ,[128,0,0] \
    ,[128,0,128] \
    ,[128,0,128] \
    ,[128,128,0] \
    ,[128,128,128] \
    ,[128,128,128] \
    ,[130,130,130] \
    ,[131,111,255] \
    ,[131,139,131] \
    ,[131,139,139] \
    ,[132,112,255] \
    ,[133,133,133] \
    ,[135,135,135] \
    ,[135,206,235] \
    ,[135,206,250] \
    ,[135,206,255] \
    ,[137,104,205] \
    ,[137,145,221] \
    ,[138,43,226] \
    ,[138,94,104] \
    ,[138,138,138] \
    ,[139,0,0] \
    ,[139,0,0] \
    ,[139,0,139] \
    ,[139,0,139] \
    ,[139,10,80] \
    ,[139,26,26] \
    ,[139,28,98] \
    ,[139,34,82] \
    ,[139,35,35] \
    ,[139,37,0] \
    ,[139,54,38] \
    ,[139,58,58] \
    ,[139,58,98] \
    ,[139,62,47] \
    ,[139,69,0] \
    ,[139,69,19] \
    ,[139,69,19] \
    ,[139,71,38] \
    ,[139,71,93] \
    ,[139,71,137] \
    ,[139,76,57] \
    ,[139,87,66] \
    ,[139,90,0] \
    ,[139,90,43] \
    ,[139,95,101] \
    ,[139,99,108] \
    ,[139,101,8] \
    ,[139,102,139] \
    ,[139,105,20] \
    ,[139,105,105] \
    ,[139,115,85] \
    ,[139,117,0] \
    ,[139,119,101] \
    ,[139,121,94] \
    ,[139,123,139] \
    ,[139,125,107] \
    ,[139,125,123] \
    ,[139,126,102] \
    ,[139,129,76] \
    ,[139,131,120] \
    ,[139,131,134] \
    ,[139,134,78] \
    ,[139,134,130] \
    ,[139,136,120] \
    ,[139,137,112] \
    ,[139,137,137] \
    ,[139,139,0] \
    ,[139,139,122] \
    ,[139,139,131] \
    ,[140,140,140] \
    ,[140,183,226] \
    ,[141,182,205] \
    ,[141,238,238] \
    ,[142,229,238] \
    ,[143,0,0] \
    ,[143,143,143] \
    ,[143,188,143] \
    ,[144,238,144] \
    ,[144,238,144] \
    ,[145,35,4] \
    ,[145,44,238] \
    ,[145,145,145] \
    ,[147,112,219] \
    ,[148,0,211] \
    ,[148,148,148] \
    ,[149,113,117] \
    ,[150,150,150] \
    ,[150,205,205] \
    ,[151,255,255] \
    ,[152,112,120] \
    ,[152,245,255] \
    ,[152,251,152] \
    ,[153,50,204] \
    ,[153,153,153] \
    ,[154,50,205] \
    ,[154,84,72] \
    ,[154,192,205] \
    ,[154,205,50] \
    ,[154,205,50] \
    ,[154,255,154] \
    ,[155,48,255] \
    ,[155,86,71] \
    ,[155,205,155] \
    ,[156,156,156] \
    ,[157,121,34] \
    ,[158,158,158] \
    ,[159,121,238] \
    ,[159,182,205] \
    ,[159,209,150] \
    ,[160,32,240] \
    ,[160,32,240] \
    ,[160,82,45] \
    ,[161,158,90] \
    ,[161,161,161] \
    ,[162,181,205] \
    ,[162,205,90] \
    ,[163,161,102] \
    ,[163,163,163] \
    ,[164,125,130] \
    ,[164,211,238] \
    ,[165,42,42] \
    ,[165,127,134] \
    ,[166,147,158] \
    ,[166,166,166] \
    ,[168,168,168] \
    ,[169,169,169] \
    ,[171,130,255] \
    ,[171,131,139] \
    ,[171,171,171] \
    ,[172,35,0] \
    ,[173,173,173] \
    ,[173,216,230] \
    ,[173,255,47] \
    ,[174,238,238] \
    ,[175,238,238] \
    ,[176,48,96] \
    ,[176,48,96] \
    ,[176,141,148] \
    ,[176,176,176] \
    ,[176,196,222] \
    ,[176,224,230] \
    ,[176,226,255] \
    ,[178,34,34] \
    ,[178,58,238] \
    ,[178,223,238] \
    ,[179,179,179] \
    ,[179,224,213] \
    ,[179,238,58] \
    ,[180,82,205] \
    ,[180,205,205] \
    ,[180,238,180] \
    ,[181,181,181] \
    ,[184,134,11] \
    ,[184,184,184] \
    ,[185,178,172] \
    ,[185,211,238] \
    ,[185,230,232] \
    ,[186,61,31] \
    ,[186,85,211] \
    ,[186,186,186] \
    ,[187,149,50] \
    ,[187,234,217] \
    ,[187,255,255] \
    ,[188,143,143] \
    ,[188,210,238] \
    ,[188,238,104] \
    ,[189,139,151] \
    ,[189,183,107] \
    ,[189,189,189] \
    ,[190,190,190] \
    ,[190,190,190] \
    ,[190,232,185] \
    ,[191,62,255] \
    ,[191,138,150] \
    ,[191,150,242] \
    ,[191,191,191] \
    ,[191,239,255] \
    ,[192,192,192] \
    ,[192,255,62] \
    ,[193,160,66] \
    ,[193,205,193] \
    ,[193,205,205] \
    ,[193,255,193] \
    ,[194,194,194] \
    ,[195,145,154] \
    ,[196,196,196] \
    ,[197,197,123] \
    ,[198,226,255] \
    ,[199,21,133] \
    ,[199,199,199] \
    ,[199,208,217] \
    ,[200,200,128] \
    ,[200,201,131] \
    ,[200,211,217] \
    ,[201,201,201] \
    ,[202,225,255] \
    ,[202,255,112] \
    ,[203,152,161] \
    ,[203,211,207] \
    ,[204,204,204] \
    ,[205,0,0] \
    ,[205,0,205] \
    ,[205,16,118] \
    ,[205,38,38] \
    ,[205,41,144] \
    ,[205,50,120] \
    ,[205,51,51] \
    ,[205,55,0] \
    ,[205,79,57] \
    ,[205,85,85] \
    ,[205,91,69] \
    ,[205,92,92] \
    ,[205,96,144] \
    ,[205,102,0] \
    ,[205,102,29] \
    ,[205,104,57] \
    ,[205,104,137] \
    ,[205,105,201] \
    ,[205,112,84] \
    ,[205,129,98] \
    ,[205,133,0] \
    ,[205,133,63] \
    ,[205,133,63] \
    ,[205,140,149] \
    ,[205,145,158] \
    ,[205,149,12] \
    ,[205,150,205] \
    ,[205,155,29] \
    ,[205,155,155] \
    ,[205,156,0] \
    ,[205,170,125] \
    ,[205,173,0] \
    ,[205,175,149] \
    ,[205,179,139] \
    ,[205,181,205] \
    ,[205,183,158] \
    ,[205,183,181] \
    ,[205,186,150] \
    ,[205,190,112] \
    ,[205,192,176] \
    ,[205,193,197] \
    ,[205,197,191] \
    ,[205,198,115] \
    ,[205,200,177] \
    ,[205,201,165] \
    ,[205,201,201] \
    ,[205,205,0] \
    ,[205,205,180] \
    ,[205,205,193] \
    ,[207,207,207] \
    ,[207,209,140] \
    ,[207,209,214] \
    ,[208,32,144] \
    ,[208,207,214] \
    ,[209,69,38] \
    ,[209,95,238] \
    ,[209,156,166] \
    ,[209,157,169] \
    ,[209,209,209] \
    ,[209,238,238] \
    ,[210,105,30] \
    ,[210,180,140] \
    ,[210,203,197] \
    ,[211,182,141] \
    ,[211,211,211] \
    ,[212,161,168] \
    ,[212,212,212] \
    ,[213,224,216] \
    ,[213,232,185] \
    ,[214,214,214] \
    ,[215,163,175] \
    ,[216,191,216] \
    ,[217,166,175] \
    ,[217,217,217] \
    ,[218,112,214] \
    ,[218,165,32] \
    ,[219,112,147] \
    ,[219,166,176] \
    ,[219,218,208] \
    ,[219,219,219] \
    ,[220,20,60] \
    ,[220,71,39] \
    ,[220,169,179] \
    ,[220,220,220] \
    ,[220,229,236] \
    ,[221,160,221] \
    ,[222,184,135] \
    ,[222,222,222] \
    ,[223,226,164] \
    ,[224,102,255] \
    ,[224,143,198] \
    ,[224,145,147] \
    ,[224,175,130] \
    ,[224,224,224] \
    ,[224,238,224] \
    ,[224,238,238] \
    ,[224,255,255] \
    ,[224,255,255] \
    ,[226,223,197] \
    ,[227,69,32] \
    ,[227,227,227] \
    ,[229,229,229] \
    ,[230,230,250] \
    ,[231,234,206] \
    ,[232,218,185] \
    ,[232,230,185] \
    ,[232,232,232] \
    ,[232,236,176] \
    ,[233,150,122] \
    ,[234,138,138] \
    ,[235,197,12] \
    ,[235,235,235] \
    ,[237,145,110] \
    ,[237,189,219] \
    ,[237,236,171] \
    ,[237,237,237] \
    ,[237,241,182] \
    ,[238,0,0] \
    ,[238,0,238] \
    ,[238,18,137] \
    ,[238,44,44] \
    ,[238,48,167] \
    ,[238,58,140] \
    ,[238,59,59] \
    ,[238,64,0] \
    ,[238,92,66] \
    ,[238,99,99] \
    ,[238,106,80] \
    ,[238,106,167] \
    ,[238,118,0] \
    ,[238,118,33] \
    ,[238,121,66] \
    ,[238,121,159] \
    ,[238,122,233] \
    ,[238,130,98] \
    ,[238,130,238] \
    ,[238,149,114] \
    ,[238,154,0] \
    ,[238,154,73] \
    ,[238,162,173] \
    ,[238,169,184] \
    ,[238,173,14] \
    ,[238,174,238] \
    ,[238,180,34] \
    ,[238,180,180] \
    ,[238,197,145] \
    ,[238,201,0] \
    ,[238,203,173] \
    ,[238,207,161] \
    ,[238,210,238] \
    ,[238,213,183] \
    ,[238,213,210] \
    ,[238,216,174] \
    ,[238,220,130] \
    ,[238,221,130] \
    ,[238,223,204] \
    ,[238,224,229] \
    ,[238,229,222] \
    ,[238,230,133] \
    ,[238,232,170] \
    ,[238,232,205] \
    ,[238,233,191] \
    ,[238,233,233] \
    ,[238,238,0] \
    ,[238,238,209] \
    ,[238,238,224] \
    ,[240,77,44] \
    ,[240,91,54] \
    ,[240,128,128] \
    ,[240,230,140] \
    ,[240,240,240] \
    ,[240,248,255] \
    ,[240,255,240] \
    ,[240,255,240] \
    ,[240,255,255] \
    ,[240,255,255] \
    ,[241,246,188] \
    ,[242,242,242] \
    ,[244,164,96] \
    ,[245,222,179] \
    ,[245,245,220] \
    ,[245,245,245] \
    ,[245,245,245] \
    ,[245,255,250] \
    ,[247,247,247] \
    ,[248,248,255] \
    ,[249,157,199] \
    ,[250,128,114] \
    ,[250,235,215] \
    ,[250,240,230] \
    ,[250,250,210] \
    ,[250,250,250] \
    ,[251,108,74] \
    ,[251,229,3] \
    ,[251,251,192] \
    ,[251,254,196] \
    ,[252,252,252] \
    ,[253,93,57] \
    ,[253,245,230] \
    ,[254,106,67] \
    ,[255,0,0] \
    ,[255,0,0] \
    ,[255,0,255] \
    ,[255,0,255] \
    ,[255,0,255] \
    ,[255,20,147] \
    ,[255,20,147] \
    ,[255,48,48] \
    ,[255,52,179] \
    ,[255,62,150] \
    ,[255,64,64] \
    ,[255,69,0] \
    ,[255,69,0] \
    ,[255,99,71] \
    ,[255,99,71] \
    ,[255,105,180] \
    ,[255,106,106] \
    ,[255,110,180] \
    ,[255,114,86] \
    ,[255,127,0] \
    ,[255,127,36] \
    ,[255,127,80] \
    ,[255,129,101] \
    ,[255,130,71] \
    ,[255,130,171] \
    ,[255,131,250] \
    ,[255,140,0] \
    ,[255,140,105] \
    ,[255,160,122] \
    ,[255,160,122] \
    ,[255,165,0] \
    ,[255,165,0] \
    ,[255,165,79] \
    ,[255,174,185] \
    ,[255,181,197] \
    ,[255,182,193] \
    ,[255,185,15] \
    ,[255,187,255] \
    ,[255,192,203] \
    ,[255,193,37] \
    ,[255,193,193] \
    ,[255,211,155] \
    ,[255,215,0] \
    ,[255,215,0] \
    ,[255,218,185] \
    ,[255,218,185] \
    ,[255,222,173] \
    ,[255,222,173] \
    ,[255,225,255] \
    ,[255,228,181] \
    ,[255,228,196] \
    ,[255,228,196] \
    ,[255,228,225] \
    ,[255,228,225] \
    ,[255,231,186] \
    ,[255,235,205] \
    ,[255,236,139] \
    ,[255,239,213] \
    ,[255,239,219] \
    ,[255,240,245] \
    ,[255,240,245] \
    ,[255,245,238] \
    ,[255,245,238] \
    ,[255,246,143] \
    ,[255,248,220] \
    ,[255,248,220] \
    ,[255,250,205] \
    ,[255,250,205] \
    ,[255,250,240] \
    ,[255,250,250] \
    ,[255,250,250] \
    ,[255,255,0] \
    ,[255,255,0] \
    ,[255,255,224] \
    ,[255,255,224] \
    ,[255,255,240] \
    ,[255,255,240] \
    ,[255,255,255] \
    ,[255,255,255] \
    ])
    ### End of paste ###

    # Dictionary of colornames indexed by key _Hex:
    # See https://bdhacker.wordpress.com/2010/02/27/python-tutorial-dictionaries-key-value-pair-maps-basics/
    HexNameDict = { \
      "#000000":"Black" \
    ,"#000000":"Black" \
    ,"#000080":"Blue" \
    ,"#000080":"Blue" \
    ,"#00008B":"Blue" \
    ,"#00008B":"Blue" \
    ,"#0000CD":"Blue" \
    ,"#0000CD":"Blue" \
    ,"#0000EE":"Blue" \
    ,"#0000FF":"Blue" \
    ,"#0000FF":"Blue" \
    ,"#003866":"Indigo" \
    ,"#006400":"Green" \
    ,"#00688B":"Blue" \
    ,"#006B3C":"Green" \
    ,"#008000":"Green" \
    ,"#008000":"Green" \
    ,"#008080":"Turquoise" \
    ,"#00868B":"Turquoise" \
    ,"#008B00":"Green" \
    ,"#008B45":"Green" \
    ,"#008B8B":"Turquoise" \
    ,"#008B8B":"Turquoise" \
    ,"#009ACD":"Blue" \
    ,"#009B91":"Turquoise" \
    ,"#00B2EE":"Blue" \
    ,"#00BFFF":"Blue" \
    ,"#00BFFF":"Blue" \
    ,"#00C5CD":"Turquoise" \
    ,"#00CD00":"Light green" \
    ,"#00CD66":"Green" \
    ,"#00CDCD":"Turquoise" \
    ,"#00CED1":"Turquoise" \
    ,"#00E5EE":"Turquoise" \
    ,"#00EE00":"Light green" \
    ,"#00EE76":"Light green" \
    ,"#00EEEE":"Turquoise" \
    ,"#00F5FF":"Turquoise" \
    ,"#00FA9A":"Light green" \
    ,"#00FF00":"Light green" \
    ,"#00FF00":"Light green" \
    ,"#00FF00":"Light green" \
    ,"#00FF00":"Light green" \
    ,"#00FF7F":"Light green" \
    ,"#00FF7F":"Light green" \
    ,"#00FFFF":"Turquoise" \
    ,"#00FFFF":"Turquoise" \
    ,"#00FFFF":"Turquoise" \
    ,"#026666":"Blue" \
    ,"#03006B":"Indigo" \
    ,"#030303":"Black" \
    ,"#034625":"Green" \
    ,"#03522B":"Green" \
    ,"#050505":"Black" \
    ,"#054927":"Green" \
    ,"#080808":"Black" \
    ,"#0A0A0A":"Black" \
    ,"#C1B8B3":"White" \
    ,"#0D0D0D":"Black" \
    ,"#0F0F0F":"Black" \
    ,"#104E8B":"Blue" \
    ,"#116000":"Green" \
    ,"#121212":"Black" \
    ,"#141414":"Black" \
    ,"#162442":"Blue" \
    ,"#171717":"Black" \
    ,"#174D84":"Blue" \
    ,"#183F72":"Blue" \
    ,"#1874CD":"Blue" \
    ,"#191970":"Blue" \
    ,"#1A1A1A":"Black" \
    ,"#1C1C1C":"Black" \
    ,"#1C86EE":"Blue" \
    ,"#1E90FF":"Blue" \
    ,"#1E90FF":"Blue" \
    ,"#1F1F1F":"Black" \
    ,"#20B2AA":"Turquoise" \
    ,"#212121":"Black" \
    ,"#228B22":"Green" \
    ,"#242424":"Black" \
    ,"#262626":"Black" \
    ,"#27408B":"Blue" \
    ,"#292929":"Black" \
    ,"#2B2B2B":"Black" \
    ,"#2E2E2E":"Black" \
    ,"#2E8B57":"Green" \
    ,"#2E8B57":"Green" \
    ,"#2F4F4F":"Turquoise" \
    ,"#303030":"Black" \
    ,"#32CD32":"Light green" \
    ,"#333333":"Black" \
    ,"#363636":"Black" \
    ,"#36648B":"Blue" \
    ,"#383838":"Black" \
    ,"#3A5FCD":"Blue" \
    ,"#3B3B3B":"Black" \
    ,"#3CB371":"Green" \
    ,"#3D3D3D":"Black" \
    ,"#3F604D":"Green" \
    ,"#404040":"Gray" \
    ,"#40E0D0":"Turquoise" \
    ,"#4169E1":"Blue" \
    ,"#424242":"Gray" \
    ,"#436EEE":"Blue" \
    ,"#43CD80":"Light green" \
    ,"#447C33":"Green" \
    ,"#454545":"Gray" \
    ,"#458B00":"Green" \
    ,"#458B74":"Green" \
    ,"#466835":"Green" \
    ,"#4682B4":"Blue" \
    ,"#473C8B":"Purple" \
    ,"#474747":"Gray" \
    ,"#483D8B":"Purple" \
    ,"#4876FF":"Blue" \
    ,"#48D1CC":"Turquoise" \
    ,"#4A4A4A":"Gray" \
    ,"#4A708B":"Blue" \
    ,"#4B0082":"Indigo" \
    ,"#4D4D4D":"Gray" \
    ,"#4EEE94":"Light green" \
    ,"#4F4F4F":"Gray" \
    ,"#4F94CD":"Blue" \
    ,"#525252":"Gray" \
    ,"#528B8B":"Blue" \
    ,"#53868B":"Blue" \
    ,"#545454":"Gray" \
    ,"#547F74":"Green" \
    ,"#548B54":"Green" \
    ,"#54FF9F":"Light green" \
    ,"#551A8B":"Indigo" \
    ,"#556B2F":"Green" \
    ,"#575757":"Gray" \
    ,"#595959":"Gray" \
    ,"#5B0001":"Brown" \
    ,"#5C5C5C":"Gray" \
    ,"#5CACEE":"Blue" \
    ,"#5D478B":"Purple" \
    ,"#5E5E5E":"Gray" \
    ,"#5F9EA0":"Blue" \
    ,"#607B8B":"Blue" \
    ,"#616161":"Gray" \
    ,"#628B49":"Light green" \
    ,"#636363":"Gray" \
    ,"#63B8FF":"Blue" \
    ,"#6495ED":"Blue" \
    ,"#663399":"Purple" \
    ,"#668B8B":"Turquoise" \
    ,"#66CD00":"Light green" \
    ,"#66CDAA":"Turquoise" \
    ,"#66CDAA":"Turquoise" \
    ,"#68228B":"Purple" \
    ,"#68838B":"White" \
    ,"#689D61":"Light green" \
    ,"#6959CD":"Indigo" \
    ,"#696969":"Gray" \
    ,"#696969":"Gray" \
    ,"#698B22":"Green" \
    ,"#698B69":"Green" \
    ,"#6A5ACD":"Indigo" \
    ,"#6B6B6B":"Gray" \
    ,"#6B8E23":"Green" \
    ,"#6C7B8B":"White" \
    ,"#6CA6CD":"Blue" \
    ,"#6E6E6E":"Gray" \
    ,"#6E7B8B":"White" \
    ,"#6E8B3D":"Green" \
    ,"#6F9D55":"Light green" \
    ,"#707070":"Gray" \
    ,"#708090":"White" \
    ,"#737373":"Gray" \
    ,"#757575":"Gray" \
    ,"#76EE00":"Light green" \
    ,"#76EEC6":"Turquoise" \
    ,"#778899":"White" \
    ,"#77A870":"Light green" \
    ,"#787878":"Gray" \
    ,"#78AF6E":"Light green" \
    ,"#79CDCD":"Turquoise" \
    ,"#7A006A":"Purple" \
    ,"#7A378B":"Light purple" \
    ,"#7A67EE":"Purple" \
    ,"#7A7A7A":"Gray" \
    ,"#7A8B8B":"White" \
    ,"#7AC5CD":"Turquoise" \
    ,"#7B68EE":"Purple" \
    ,"#7CCD7C":"Green" \
    ,"#7CFC00":"Light green" \
    ,"#7D26CD":"Purple" \
    ,"#7D7D7D":"Gray" \
    ,"#7EB572":"Light green" \
    ,"#7EC0EE":"Blue" \
    ,"#7F7F7F":"Gray" \
    ,"#7FFF00":"Light green" \
    ,"#7FFF00":"Light green" \
    ,"#7FFFD4":"Turquoise" \
    ,"#7FFFD4":"Turquoise" \
    ,"#800000":"Brown" \
    ,"#800000":"Brown" \
    ,"#800080":"Light purple" \
    ,"#800080":"Light purple" \
    ,"#808000":"Green" \
    ,"#808080":"Gray" \
    ,"#808080":"Gray" \
    ,"#828282":"Gray" \
    ,"#836FFF":"Purple" \
    ,"#838B83":"White" \
    ,"#838B8B":"White" \
    ,"#8470FF":"Purple" \
    ,"#858585":"Gray" \
    ,"#878787":"Gray" \
    ,"#87CEEB":"Blue" \
    ,"#87CEFA":"Blue" \
    ,"#87CEFF":"Blue" \
    ,"#8968CD":"Purple" \
    ,"#8991DD":"Indigo" \
    ,"#8A2BE2":"Purple" \
    ,"#8A5E68":"Pink" \
    ,"#8A8A8A":"White" \
    ,"#8B0000":"Red" \
    ,"#8B0000":"Red" \
    ,"#8B008B":"Light purple" \
    ,"#8B008B":"Light purple" \
    ,"#8B0A50":"Light purple" \
    ,"#8B1A1A":"Red" \
    ,"#8B1C62":"Light purple" \
    ,"#8B2252":"Light purple" \
    ,"#8B2323":"Brown" \
    ,"#8B2500":"Red" \
    ,"#8B3626":"Brown" \
    ,"#8B3A3A":"Red" \
    ,"#8B3A62":"Light purple" \
    ,"#8B3E2F":"Red" \
    ,"#8B4500":"Green" \
    ,"#8B4513":"Brown" \
    ,"#8B4513":"Brown" \
    ,"#8B4726":"Brown" \
    ,"#8B475D":"Light purple" \
    ,"#8B4789":"Purple" \
    ,"#8B4C39":"Brown" \
    ,"#8B5742":"Brown" \
    ,"#8B5A00":"Brown" \
    ,"#8B5A2B":"Brown" \
    ,"#8B5F65":"Light purple" \
    ,"#8B636C":"Light purple" \
    ,"#8B6508":"Brown" \
    ,"#8B668B":"Light purple" \
    ,"#8B6914":"Brown" \
    ,"#8B6969":"Light purple" \
    ,"#8B7355":"Brown" \
    ,"#8B7500":"Brown" \
    ,"#8B7765":"Brown" \
    ,"#8B795E":"Brown" \
    ,"#8B7B8B":"Gray" \
    ,"#8B7D6B":"Brown" \
    ,"#8B7D7B":"White" \
    ,"#8B7E66":"Brown" \
    ,"#8B814C":"Brown" \
    ,"#8B8378":"White" \
    ,"#8B8386":"White" \
    ,"#8B864E":"Brown" \
    ,"#8B8682":"White" \
    ,"#8B8878":"Brown" \
    ,"#8B8970":"Brown" \
    ,"#8B8989":"White" \
    ,"#8B8B00":"green" \
    ,"#8B8B7A":"White" \
    ,"#8B8B83":"White" \
    ,"#8C8C8C":"White" \
    ,"#8CB7E2":"Blue" \
    ,"#8DB6CD":"Blue" \
    ,"#8DEEEE":"Blue" \
    ,"#8EE5EE":"Blue" \
    ,"#8E0000":"Red" \
    ,"#8F8F8F":"White" \
    ,"#8FBC8F":"Green" \
    ,"#90EE90":"Light green" \
    ,"#90EE90":"Light green" \
    ,"#912304":"Orange" \
    ,"#912CEE":"Purple" \
    ,"#919191":"White" \
    ,"#9370DB":"Purple" \
    ,"#9400D3":"Purple" \
    ,"#949494":"White" \
    ,"#957175":"Pink" \
    ,"#969696":"White" \
    ,"#96CDCD":"Turquoise" \
    ,"#97FFFF":"Blue" \
    ,"#987078":"Pink" \
    ,"#98F5FF":"Blue" \
    ,"#98FB98":"Light green" \
    ,"#9932CC":"Purple" \
    ,"#999999":"White" \
    ,"#9A32CD":"Purple" \
    ,"#9A5448":"Pink" \
    ,"#9AC0CD":"Blue" \
    ,"#9ACD32":"Green" \
    ,"#9ACD32":"Green" \
    ,"#9AFF9A":"Light green" \
    ,"#9B30FF":"Purple" \
    ,"#9B5647":"Pink" \
    ,"#9BCD9B":"Green" \
    ,"#9C9C9C":"White" \
    ,"#9D7922":"Yellow" \
    ,"#9E9E9E":"White" \
    ,"#9F79EE":"Purple" \
    ,"#9FB6CD":"Blue" \
    ,"#9FD196":"Light green" \
    ,"#A020F0":"Purple" \
    ,"#A020F0":"Purple" \
    ,"#A0522D":"Brown" \
    ,"#A19E5A":"Yellow" \
    ,"#A1A1A1":"White" \
    ,"#A2B5CD":"Blue" \
    ,"#A2CD5A":"Light green" \
    ,"#A3A166":"Yellow" \
    ,"#A3A3A3":"White" \
    ,"#A57F86":"Pink" \
    ,"#A6939E":"Pink" \
    ,"#A47D82":"Pink" \
    ,"#A4D3EE":"Blue" \
    ,"#A52A2A":"Brown" \
    ,"#A6A6A6":"White" \
    ,"#A8A8A8":"White" \
    ,"#A9A9A9":"White" \
    ,"#AB82FF":"Purple" \
    ,"#AB838B":"Pink" \
    ,"#ABABAB":"White" \
    ,"#AC2300":"Orange" \
    ,"#ADADAD":"White" \
    ,"#ADD8E6":"Turquoise" \
    ,"#ADFF2F":"Light green" \
    ,"#AEEEEE":"Turquoise" \
    ,"#AFEEEE":"Turquoise" \
    ,"#B03060":"Light purple" \
    ,"#B03060":"Light purple" \
    ,"#B08D94":"Pink " \
    ,"#B0B0B0":"White" \
    ,"#B0C4DE":"Turquoise" \
    ,"#B0E0E6":"Turquoise" \
    ,"#B0E2FF":"Turquoise" \
    ,"#B22222":"Red" \
    ,"#B23AEE":"Purple" \
    ,"#B2DFEE":"Turquoise" \
    ,"#B3B3B3":"White" \
    ,"#B3E0D5":"Turquoise" \
    ,"#B3EE3A":"Light green" \
    ,"#B452CD":"Purple" \
    ,"#B4CDCD":"Turquoise" \
    ,"#B4EEB4":"Light green" \
    ,"#B5B5B5":"White" \
    ,"#B8860B":"Brown" \
    ,"#B8B8B8":"White" \
    ,"#B9B2AC":"White" \
    ,"#B9D3EE":"Turquoise" \
    ,"#B9E6E8":"Blue" \
    ,"#BA3D1F":"Orange" \
    ,"#BA55D3":"Purple" \
    ,"#BABABA":"White" \
    ,"#BB9532":"Yellow" \
    ,"#BBEAD9":"Turquoise" \
    ,"#BBFFFF":"Turquoise" \
    ,"#BC8F8F":"Pink" \
    ,"#BCD2EE":"Turquoise" \
    ,"#BCEE68":"Light green" \
    ,"#BD8B97":"Pink" \
    ,"#BDB76B":"Yellow" \
    ,"#BDBDBD":"White" \
    ,"#BEBEBE":"White" \
    ,"#BEBEBE":"White" \
    ,"#BEE8B9":"Light green" \
    ,"#BF3EFF":"Purple" \
    ,"#BF8A96":"Pink" \
    ,"#BF96F2":"Purple" \
    ,"#BFBFBF":"White" \
    ,"#BFEFFF":"Turquoise" \
    ,"#C0C0C0":"White" \
    ,"#C0FF3E":"Light green" \
    ,"#C1A042":"Yellow" \
    ,"#C1CDC1":"White" \
    ,"#C1CDCD":"White" \
    ,"#C1FFC1":"Light green" \
    ,"#C2C2C2":"White" \
    ,"#C3919A":"Pink" \
    ,"#C4C4C4":"White" \
    ,"#C5C57B":"Yellow" \
    ,"#C6E2FF":"Turquoise" \
    ,"#C71585":"Light purple" \
    ,"#C7C7C7":"White" \
    ,"#C7D0D9":"White" \
    ,"#C8C880":"Yellow" \
    ,"#C8C983":"Yellow" \
    ,"#C8D3D9":"White" \
    ,"#C9C9C9":"White" \
    ,"#CAE1FF":"Turquoise" \
    ,"#CAFF70":"Light green" \
    ,"#CB98A1":"Pink" \
    ,"#CBD3CF":"White" \
    ,"#CCCCCC":"White" \
    ,"#CD0000":"Red" \
    ,"#CD00CD":"Light purple" \
    ,"#CD1076":"purple" \
    ,"#CD2626":"Red" \
    ,"#CD2990":"Purple" \
    ,"#CD3278":"Purple" \
    ,"#CD3333":"Red" \
    ,"#CD3700":"Orange" \
    ,"#CD4F39":"Orange" \
    ,"#CD5555":"Pink" \
    ,"#CD5B45":"Pink" \
    ,"#CD5C5C":"Pink" \
    ,"#CD6090":"Light purple" \
    ,"#CD6600":"Orange" \
    ,"#CD661D":"Orange" \
    ,"#CD6839":"Orange" \
    ,"#CD6889":"Light purple" \
    ,"#CD69C9":"Purple" \
    ,"#CD7054":"Orange" \
    ,"#CD8162":"Brown" \
    ,"#CD8500":"Orange" \
    ,"#CD853F":"Brown" \
    ,"#CD853F":"Brown" \
    ,"#CD8C95":"Pink" \
    ,"#CD919E":"Pink" \
    ,"#CD950C":"Brown" \
    ,"#CD96CD":"Pink" \
    ,"#CD9B1D":"Yellow" \
    ,"#CD9B9B":"Pink" \
    ,"#CD9C00":"Yellow" \
    ,"#CDAA7D":"Brown" \
    ,"#CDAD00":"Yellow" \
    ,"#CDAF95":"Brown" \
    ,"#CDB38B":"Brown" \
    ,"#CDB5CD":"Light purple" \
    ,"#CDB79E":"Brown" \
    ,"#CDB7B5":"Pink" \
    ,"#CDBA96":"Brown" \
    ,"#CDBE70":"Brown" \
    ,"#CDC0B0":"Brown" \
    ,"#CDC1C5":"White" \
    ,"#CDC5BF":"White" \
    ,"#CDC673":"Yellow" \
    ,"#CDC8B1":"White" \
    ,"#CDC9A5":"White" \
    ,"#CDC9C9":"White" \
    ,"#CDCD00":"Yellow" \
    ,"#CDCDB4":"White" \
    ,"#CDCDC1":"White" \
    ,"#CFCFCF":"White" \
    ,"#CFD18C":"Yellow" \
    ,"#CFD1D6":"White" \
    ,"#D02090":"Light purple" \
    ,"#D0CFD6":"White" \
    ,"#D14526":"Orange" \
    ,"#D15FEE":"Light purple" \
    ,"#D19CA6":"Pink" \
    ,"#D19DA9":"Pink" \
    ,"#D1D1D1":"White" \
    ,"#D1EEEE":"Turquoise" \
    ,"#D2691E":"Brown" \
    ,"#D2B48C":"Brown" \
    ,"#D2CBC5":"White" \
    ,"#D3B68D":"Orange" \
    ,"#D3D3D3":"White" \
    ,"#D4A1A8":"Pink" \
    ,"#D4D4D4":"White" \
    ,"#D5E0D8":"White" \
    ,"#D5E8B9":"Light green" \
    ,"#D6D6D6":"White" \
    ,"#D7A3AF":"Pink" \
    ,"#D8BFD8":"Pink" \
    ,"#D9A6AF":"Pink" \
    ,"#D9D9D9":"White" \
    ,"#DA70D6":"Pink" \
    ,"#DAA520":"Orange" \
    ,"#DB7093":"Pink" \
    ,"#DBA6B0":"Pink" \
    ,"#DBDAD0":"White" \
    ,"#DBDBDB":"White" \
    ,"#DC143C":"Red" \
    ,"#DC4727":"Orange" \
    ,"#DCA9B3":"Pink" \
    ,"#DCDCDC":"White" \
    ,"#DCE5EC":"White" \
    ,"#DDA0DD":"Pink" \
    ,"#DEB887":"Brown" \
    ,"#DEDEDE":"White" \
    ,"#DFE2A4":"Yellow" \
    ,"#E066FF":"Pink" \
    ,"#E08FC6":"Pink" \
    ,"#E09193":"Pink" \
    ,"#E0AF82":"Orange" \
    ,"#E0E0E0":"White" \
    ,"#E0EEE0":"Light green" \
    ,"#E0EEEE":"White" \
    ,"#E0FFFF":"Turquoise" \
    ,"#E0FFFF":"Turquoise" \
    ,"#E2DFC5":"White" \
    ,"#E34520":"Orange" \
    ,"#E3E3E3":"White" \
    ,"#E5E5E5":"White" \
    ,"#E6E6FA":"Light purple" \
    ,"#E7EACE":"White" \
    ,"#E8DAB9":"Yellow" \
    ,"#E8E6B9":"Yellow" \
    ,"#E8E8E8":"White" \
    ,"#E8ECB0":"Yellow" \
    ,"#E9967A":"Brown" \
    ,"#EA8A8A":"Pink" \
    ,"#EBC50C":"Yellow" \
    ,"#EBEBEB":"White" \
    ,"#ED916E":"Orange" \
    ,"#EDBDDB":"Pink" \
    ,"#EDECAB":"Yellow" \
    ,"#EDEDED":"White" \
    ,"#EDF1B6":"Yellow" \
    ,"#EE0000":"Red" \
    ,"#EE00EE":"Pink" \
    ,"#EE1289":"Light purple" \
    ,"#EE2C2C":"Red" \
    ,"#EE30A7":"Pink" \
    ,"#EE3A8C":"Pink" \
    ,"#EE3B3B":"Red" \
    ,"#EE4000":"Red" \
    ,"#EE5C42":"Orange" \
    ,"#EE6363":"Pink" \
    ,"#EE6A50":"Orange" \
    ,"#EE6AA7":"Pink" \
    ,"#EE7600":"Orange" \
    ,"#EE7621":"Orange" \
    ,"#EE7942":"Orange" \
    ,"#EE799F":"Pink" \
    ,"#EE7AE9":"Purple" \
    ,"#EE8262":"Orange" \
    ,"#EE82EE":"Purple" \
    ,"#EE9572":"Orange" \
    ,"#EE9A00":"Orange" \
    ,"#EE9A49":"Orange" \
    ,"#EEA2AD":"Pink" \
    ,"#EEA9B8":"Pink" \
    ,"#EEAD0E":"Orange" \
    ,"#EEAEEE":"Purple" \
    ,"#EEB422":"Yellow" \
    ,"#EEB4B4":"Pink" \
    ,"#EEC591":"Orange" \
    ,"#EEC900":"Yellow" \
    ,"#EECBAD":"Brown" \
    ,"#EECFA1":"Brown" \
    ,"#EED2EE":"Purple" \
    ,"#EED5B7":"Brown" \
    ,"#EED5D2":"Brown" \
    ,"#EED8AE":"Brown" \
    ,"#EEDC82":"Yellow" \
    ,"#EEDD82":"Yellow" \
    ,"#EEDFCC":"Brown" \
    ,"#EEE0E5":"Light purple" \
    ,"#EEE5DE":"White" \
    ,"#EEE685":"Yellow" \
    ,"#EEE8AA":"Yellow" \
    ,"#EEE8CD":"Brown" \
    ,"#EEE9BF":"Light green" \
    ,"#EEE9E9":"White" \
    ,"#EEEE00":"yellow" \
    ,"#EEEED1":"Light green" \
    ,"#EEEEE0":"White" \
    ,"#F04D2C":"Orange" \
    ,"#F05B36":"Orange" \
    ,"#F08080":"Red" \
    ,"#F0E68C":"Yellow" \
    ,"#F0F0F0":"White" \
    ,"#F0F8FF":"Blue" \
    ,"#F0FFF0":"Light green" \
    ,"#F0FFF0":"Light green" \
    ,"#F0FFFF":"Blue" \
    ,"#F0FFFF":"Blue" \
    ,"#F1F6BC":"Yellow" \
    ,"#F2F2F2":"White" \
    ,"#F4A460":"Orange" \
    ,"#F5DEB3":"Brown" \
    ,"#F5F5DC":"Light green" \
    ,"#F5F5F5":"White" \
    ,"#F5F5F5":"White" \
    ,"#F5FFFA":"Light green" \
    ,"#F7F7F7":"White" \
    ,"#F8F8FF":"White" \
    ,"#F99DC7":"Pink" \
    ,"#FA8072":"Red" \
    ,"#FAEBD7":"Brown" \
    ,"#FAF0E6":"Yellow" \
    ,"#FAFAD2":"Yellow" \
    ,"#FAFAFA":"White" \
    ,"#FB6C4A":"Orange" \
    ,"#FBE503":"Yellow" \
    ,"#FBFBC0":"Yellow" \
    ,"#FBFEC4":"Yellow" \
    ,"#FCFCFC":"White" \
    ,"#FD5D39":"Orange" \
    ,"#FDF5E6":"White" \
    ,"#FE6743":"Orange" \
    ,"#FF0000":"Red" \
    ,"#FF0000":"Red" \
    ,"#FF00FF":"Purple" \
    ,"#FF00FF":"Purple" \
    ,"#FF00FF":"Purple" \
    ,"#FF1493":"Pink" \
    ,"#FF1493":"Pink" \
    ,"#FF3030":"Red" \
    ,"#FF34B3":"Pink" \
    ,"#FF3E96":"Pink" \
    ,"#FF4040":"Red" \
    ,"#FF4500":"Red" \
    ,"#FF4500":"Red" \
    ,"#FF6347":"Red" \
    ,"#FF6347":"Red" \
    ,"#FF69B4":"Pink" \
    ,"#FF6A6A":"Red" \
    ,"#FF6EB4":"Pink" \
    ,"#FF7256":"Orange" \
    ,"#FF7F00":"Orange" \
    ,"#FF7F24":"Orange" \
    ,"#FF7F50":"Orange" \
    ,"#FF8165":"Orange" \
    ,"#FF8247":"Orange" \
    ,"#FF82AB":"Pink" \
    ,"#FF83FA":"Light purple" \
    ,"#FF8C00":"Orange" \
    ,"#FF8C69":"Orange" \
    ,"#FFA07A":"Orange" \
    ,"#FFA07A":"Orange" \
    ,"#FFA500":"Orange" \
    ,"#FFA500":"Orange" \
    ,"#FFA54F":"Orange" \
    ,"#FFAEB9":"Pink" \
    ,"#FFB5C5":"Pink" \
    ,"#FFB6C1":"Pink" \
    ,"#FFB90F":"Orange" \
    ,"#FFBBFF":"Light purple" \
    ,"#FFC0CB":"Pink" \
    ,"#FFC125":"Yellow" \
    ,"#FFC1C1":"Pink" \
    ,"#FFD39B":"Brown" \
    ,"#FFD700":"Yellow" \
    ,"#FFD700":"Yellow" \
    ,"#FFDAB9":"Brown" \
    ,"#FFDAB9":"Brown" \
    ,"#FFDEAD":"Brown" \
    ,"#FFDEAD":"Brown" \
    ,"#FFE1FF":"Light purple" \
    ,"#FFE4B5":"Orange" \
    ,"#FFE4C4":"Orange" \
    ,"#FFE4C4":"Orange" \
    ,"#FFE4E1":"Pink" \
    ,"#FFE4E1":"Pink" \
    ,"#FFE7BA":"Brown" \
    ,"#FFEBCD":"Brown" \
    ,"#FFEC8B":"Yellow" \
    ,"#FFEFD5":"Brown" \
    ,"#FFEFDB":"Brown" \
    ,"#FFF0F5":"Ligh purple" \
    ,"#FFF0F5":"Ligh purple" \
    ,"#FFF5EE":"White" \
    ,"#FFF5EE":"White" \
    ,"#FFF68F":"Yellow" \
    ,"#FFF8DC":"Yellow" \
    ,"#FFF8DC":"Yellow" \
    ,"#FFFACD":"Yellow" \
    ,"#FFFACD":"Yellow" \
    ,"#FFFAF0":"White" \
    ,"#FFFAFA":"White" \
    ,"#FFFAFA":"White" \
    ,"#FFFF00":"Yellow" \
    ,"#FFFF00":"Yellow" \
    ,"#FFFFE0":"Light green" \
    ,"#FFFFE0":"Light green" \
    ,"#FFFFF0":"White" \
    ,"#FFFFF0":"White" \
    ,"#FFFFFF":"White" \
    ,"#FFFFFF":"White" \
    }

    # TODO: Test calls using variety of RGB input values
    # TODO: Change to call argument with the point to find
    # pt = [221,183,134] # approximate to
    #pt = [0,0,0] # example needing zerofill
    pt = [red,green,blue] # = "burlywood","#DEB887"]
    # pt = [222,184,135] # = "burlywood","#DEB887"]
    # pt = [154,205,50] # = OliveDrab

    # Lookup color name using Hex:ColorName dictionary:
    NearestRGB = (RGB[spatial.KDTree(RGB).query(pt)[1]])

    # TODO: Calculate Hex from pt. (upper case letters)
    # Instead of str(hex(pt[0])[2:]) in Python2, this is Python3 compatible:
    s = '#' \
        + format(NearestRGB[0],'x').zfill(2) \
        + format(NearestRGB[1],'x').zfill(2) \
        + format(NearestRGB[2],'x').zfill(2)
    ColorHex = s.upper() # "#8B7355"  # "#8B7355"
    ColorDiff = \
         '('+'{0:+d}'.format(NearestRGB[0]-pt[0]) \
        +','+'{0:+d}'.format(NearestRGB[1]-pt[1]) \
        +','+'{0:+d}'.format(NearestRGB[2]-pt[2]) \
        +')'
    try: ## TODO: try catch block per https://wiki.python.org/moin/HandlingExceptions
        ColorName=HexNameDict[ColorHex]
    except:
        ColorName="not found"
    #print('Nearest color name to input RGB ' \
     #   + str(pt) \
      #  + ' is "'+ ColorName +'"' \
      #  +' '+ ColorHex  \
      #  +' '+ str(NearestRGB) \
      #  +', '+ ColorDiff \
      #  +'.')
   # print(ColorName)
    return ColorName
    #return ColorName
    # print 'Is the above correct?'

    # TODO: Return HexNameDict[ColorHex] to caller.

    # TODO: Print distance between input and output RGB.
